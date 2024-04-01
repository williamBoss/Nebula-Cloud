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
      permissions: string[]
    }

    const state: State = reactive({
      userName: '',
      nickName: '',
      email: '',
      phonenumber: '',
      sex: '',
      avatar: '',
      admin: false,
      roleNames: [],
      permissions: []
    })

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
