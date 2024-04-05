import axios from 'axios'
import { showMessage } from './status.ts' // 引入状态码文件
import { ElMessage } from 'element-plus'
import pinia, { globalStore } from '@/store/index.ts'
import router from '@/router/index.js'
import { LOGIN_URL } from '@/config/config.ts'

/* 实例化请求配置 */
const instance = axios.create({
  // 接口超时时间
  timeout: 1000 * 60,
  // 请求的base地址
  baseURL: import.meta.env.VITE_AXIOS_BASE_URL,
  // 表示跨域请求时是否需要使用凭证
  withCredentials: false
})

/**
 * 请求拦截器
 * 每次请求前，如果存在token则在请求头中携带token
 */
instance.interceptors.request.use(
  (config) => {
    config.headers['Content-Type'] = 'application/json; charset=utf-8'
    const global = globalStore()
    // 登录流程控制中，根据本地是否存在token判断用户的登录情况
    // 但是即使token存在，也有可能token是过期的，所以在每次的请求头中携带token
    // 后台根据携带的token判断用户的登录情况，并返回给我们对应的状态码
    // 而后我们可以在响应拦截器中，根据状态码进行一些统一的操作。
    const token = global.token
    if (token) {
      console.log(token)
      config.headers.Authorization = `Bearer ${token}` // 让每个请求携带自定义token 请根据实际情况自行修改
    } else {
      router.push(LOGIN_URL).catch((err) => {
        console.error(err)
      })
    }
    if (config.method === 'get' && config.params) {
      let url = config.url + '?'
      for (const propName of Object.keys(config.params)) {
        const value = config.params[propName]
        const part = encodeURIComponent(propName) + '='
        if (value !== null && typeof value !== 'undefined') {
          if (typeof value === 'object') {
            for (const key of Object.keys(value)) {
              const params = propName + '[' + key + ']'
              const subPart = encodeURIComponent(params) + '='
              url += subPart + encodeURIComponent(value[key]) + '&'
            }
          } else {
            url += part + encodeURIComponent(value) + '&'
          }
        }
      }
      url = url.slice(0, -1)
      config.params = {}
      config.url = url
    }
    return config
  },
  (error) => {
    ElMessage.error(error.data.error.message)
    return Promise.reject(error.data.error.message)
  }
)

/**
 * http response 拦截器
 */
instance.interceptors.response.use(
  (response) => {
    // 未设置状态码则默认成功状态
    const code = response.data.code || 200
    // 获取错误信息
    const msg = response.data.msg || showMessage(code)
    if ((response.status === 200 || response.status === 204) && code === 200) {
      return Promise.resolve(response)
    }
    ElMessage({
      showClose: true,
      message: msg,
      grouping: true,
      type: 'error'
    })
    if (code === 401) {
      router.push('/login').catch((err) => {
        console.error(err)
      })
    }
    return Promise.reject(response)
  },
  (error) => {
    const global = globalStore(pinia)
    const { response } = error
    if (response) {
      showMessage(response.status)
      // 超时重新请求
      const config = error.config
      // 全局的请求次数,请求的间隙
      const [RETRY_COUNT, RETRY_DELAY] = [3, 1000]
      if (config && RETRY_COUNT) {
        // 设置用于跟踪重试计数的变量
        config.__retryCount = config.__retryCount || 0
        // 检查是否已经把重试的总数用完
        if (config.__retryCount >= RETRY_COUNT) {
          return Promise.reject(response || { message: error.message })
        }
        // 增加重试计数
        config.__retryCount++
        // 创造新的Promise来处理指数后退
        const backoff = new Promise<void>((resolve) => {
          setTimeout(() => {
            resolve()
          }, RETRY_DELAY || 1)
        })
        // instance重试请求的Promise
        return backoff.then(() => {
          return instance(config)
        })
      }
      return Promise.reject(response)
    }
    // 处理断网的情况
    // eg:请求超时或断网时，更新state的network状态
    // network状态在app.vue中控制着一个全局的断网提示组件的显示隐藏
    // 后续增加断网情况下做的一些操作
    global.networkState = false
    ElMessage.warning('网络连接异常,请稍后再试!')
  }
)

// 只需要考虑单一职责，这块只封装axios
export default instance
