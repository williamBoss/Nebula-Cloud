export const showMessage = (status: number) => {
  let message = ''
  switch (status) {
    case 302:
      message = '接口重定向(302)'
      break
    case 400:
      message = '请求错误(400)'
      break
    case 401:
      message = '未授权，请重新登录(401)'
      break
    case 403:
      message = '拒绝访问(403)'
      break
    case 404:
      message = '网络请求不存在(404)'
      break
    case 408:
      message = '请求超时(408)'
      break
    case 410:
      message = '请求的资源被永久删除(410)'
      break
    case 422:
      message = '当创建一个对象时，发生一个验证错误(422)'
      break
    case 500:
      message = '服务器错误(500)'
      break
    case 501:
      message = '服务未实现(501)'
      break
    case 502:
      message = '网络错误(502)'
      break
    case 503:
      message = '服务不可用，服务器暂时过载或维护(503)'
      break
    case 504:
      message = '网络超时(504)'
      break
    case 505:
      message = 'HTTP版本不受支持(505)'
      break
    default:
      message = `连接出错(${status})!`
  }
  return `${message}，请检查网络或联系管理员！`
}
