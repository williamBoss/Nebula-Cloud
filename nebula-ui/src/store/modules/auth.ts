import { defineStore } from 'pinia'
import { getAllBreadcrumbList, getShowMenuList } from '@/utils/util.js'
import { LoginService } from '@api/sys-api.js'
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
    // 面包屑导航列表
    const breadcrumbListGet = () => getAllBreadcrumbList(state.authMenuList)
    // 需要缓存的菜单 name，用作页面 keepAlive
    // const keepAliveRouterGet = () => getKeepAliveRouterName(state.authMenuList)
    // getAuthMenuList
    const getAuthMenuList = async () => {
      const { data }: any = await LoginService.getRouters()
      state.authMenuList = data
    }
    return {
      ...toRefs(state),
      showMenuListGet,
      breadcrumbListGet,
      // keepAliveRouterGet,
      getAuthMenuList
    }
  },
  {
    persist: piniaPersistConfig('AuthState', ['authMenuList'])
  }
)
