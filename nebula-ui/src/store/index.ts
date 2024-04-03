import { DEFAULT_PRIMARY } from '@/config/config.ts'
import { createPinia, defineStore } from 'pinia'
import pinaPluginPersistence from 'pinia-plugin-persistedstate'
import { LoginService, UserService } from '@api/sys-api.ts'
import piniaPersistConfig from '@/config/persist.ts'
import { reactive, toRefs } from 'vue'
import { userStore } from '@/store/modules/user.ts'

const pinia = createPinia()
pinia.use(pinaPluginPersistence)

export const globalStore = defineStore(
  'GlobalState',
  () => {
    const state = reactive({
      count: 0,
      // token
      token: '',
      // userInfo
      userInfo: {},
      // 菜单
      menus: [],
      // element组件大小
      assemblySize: 'default',
      // language
      language: '',
      // networkState断网重试
      networkState: false,
      // themeConfig
      themeConfig: {
        // 布局切换 ==>  纵向：vertical | 经典：classic | 横向：transverse | 分栏：columns
        layout: 'vertical',
        // 默认 primary 主题颜色
        primary: DEFAULT_PRIMARY,
        // 深色模式
        isDark: false,
        // 灰色模式
        isGrey: false,
        // 色弱模式
        isWeak: false,
        // 折叠菜单
        isCollapse: false,
        // 面包屑导航
        breadcrumb: true,
        // 面包屑导航图标
        breadcrumbIcon: true,
        // 标签页
        tabs: true,
        // 标签页图标
        tabsIcon: true,
        // 页脚
        footer: true,
        // 当前页面是否全屏
        maximize: false
      }
    })

    // 获取用户信息
    const getUserInfo = () => {
      return new Promise((resolve, reject) => {
        UserService.user
          .getInfo()
          .then((data) => {
            const _userStore = userStore()
            const { menus, role, user, permissions }: any = data
            // 验证返回的roles是否是一个非空数组
            if (role) {
              _userStore.setRoleNames([role.roleName])
              _userStore.setPermissions(permissions)
            } else {
              _userStore.setRoleNames(['ROLE_DEFAULT'])
            }
            if (user.nickName !== null) {
              _userStore.setName(user.nickName)
            }
            if (user.avatar !== null) {
              _userStore.setAvatar(user.avatar)
            }
            state.userInfo = user
            state.menus = menus
            resolve(menus)
          })
          .catch((error) => {
            reject(error)
          })
      })
    }

    // 退出系统
    const logOut = () => {
      return new Promise((resolve, reject) => {
        LoginService.auth
          .logout()
          .then((res) => {
            const user = userStore()
            state.token = ''
            state.userInfo = {}
            user.setRoleNames([])
            user.setPermissions([])
            resolve(res)
          })
          .catch((error) => {
            reject(error)
          })
      })
    }

    return {
      ...toRefs(state),
      getUserInfo,
      logOut
    }
  },
  {
    persist: piniaPersistConfig('GlobalState')
  }
)

export default pinia
