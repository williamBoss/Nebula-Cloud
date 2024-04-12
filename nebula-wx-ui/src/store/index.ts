import { createPinia, defineStore } from 'pinia'
import pinaPluginPersistence from 'pinia-plugin-persistedstate'
import piniaPersistConfig from '@/config/persist.ts'
import { reactive, toRefs } from 'vue'

const pinia = createPinia()
pinia.use(pinaPluginPersistence)

export const globalStore = defineStore(
  'GlobalState',
  () => {
    const state = reactive({
      count: 0,
      // token
      token: '',
      // language
      language: '',
      // networkState断网重试
      networkState: false,
      // openid
      city: '',
      country: '',
      headImgUrl: '',
      nickName: '',
      openid: '',
      province: '',
      sex: 0,
      patient: {}
    })

    const initWechatUserInfo = (data: any) => {
      Object.assign(state, { ...data })
    }

    const clear = () => {
      Object.assign(state, {
        token: '',
        city: '',
        country: '',
        headImgUrl: '',
        nickName: '',
        openid: '',
        province: '',
        sex: 0
      })
    }

    return {
      ...toRefs(state),
      initWechatUserInfo,
      clear
    }
  },
  {
    persist: piniaPersistConfig('GlobalState')
  }
)

export default pinia
