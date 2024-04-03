import { defineStore } from 'pinia'
import { reactive, toRefs } from 'vue'
import piniaPersistConfig from '@/config/persist.js'

export const userStore = defineStore(
  'UserState',
  () => {
    interface State {
      userName: string
      nickName: string
      email: string
      phonenumber: string
      sex: string
      avatar: string
      admin: boolean
      roleNames: string[]
      // 权限集合
      permissions: string[]
      // 左侧菜单树形结构
      menuTree: any[]
      // 存在页面路由的菜单集合
      menuRouterList: any[]
    }

    const defaultState = () => ({
      userName: '',
      nickName: '',
      email: '',
      phonenumber: '',
      sex: '',
      avatar: '',
      admin: false,
      roleNames: [],
      permissions: [],
      menuTree: [],
      menuRouterList: []
    })

    const state: State = reactive(defaultState())

    //设置登录信息
    const setUserLoginInfo = (data: any) => {
      const { menus, role, user, permissions }: any = data
      // 验证返回的roles是否是一个非空数组
      if (role) {
        state.roleNames = [role.roleName]
        state.permissions = permissions
      } else {
        state.roleNames = ['ROLE_DEFAULT']
      }
      if (user.nickName) {
        state.nickName = user.nickName
      }
      if (user.avatar) {
        state.avatar = user.avatar
      }
      //菜单
      state.menuTree = menus
      //拥有路由的菜单
      state.menuRouterList = menus.filter((e: any) => e.menuType === 'C')
    }

    const setName = (nickName: string) => {
      state.nickName = nickName
    }
    const setAvatar = (avatar: string) => {
      state.avatar = avatar
    }
    const setRoleNames = (roleNames: string[]) => {
      state.roleNames = roleNames
    }
    const setPermissions = (permissions: string[]) => {
      state.permissions = permissions
    }

    return {
      ...toRefs(state),
      defaultState,
      setUserLoginInfo,
      setName,
      setAvatar,
      setRoleNames,
      setPermissions
    }
  },
  {
    persist: piniaPersistConfig('UserState')
  }
)
