import { defineStore } from 'pinia'
import { getShowMenuList } from '@/utils/util.js'
import { reactive, toRefs } from 'vue'
import piniaPersistConfig from '@/config/persist.js'

// AuthStore
export const authStore = defineStore(
  'AuthState',
  () => {
    const state = reactive({
      // menuList 作为动态路由，不会做持久化存储
      authMenuList: []
    })
    // 后端返回的菜单列表 ==> 左侧菜单栏渲染，需要去除 isHide == true
    const showMenuListGet = () => getShowMenuList(state.authMenuList)
    const setAuthMenuList = (data: []) => {
      state.authMenuList = data
    }
    return {
      ...toRefs(state),
      showMenuListGet,
      setAuthMenuList
    }
  },
  {
    persist: piniaPersistConfig('AuthState', ['authMenuList'])
  }
)
