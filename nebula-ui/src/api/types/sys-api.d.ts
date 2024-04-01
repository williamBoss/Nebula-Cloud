export interface ApiResponse<T> {
  success: boolean
  code: number
  msg: string
  data: T
}

export interface Pageable<T> {
  records: Array<T>
  pageNumber: string
  pageSize: string
  totalPage?: string
  totalRow: string
}

interface BaseMenu {
  menuId: string | number
  menuName: string
  parentId: string
  orderNum: number
  path: string
  component: string
  queryParam: string
  isFrame: number
  isCache: number
  menuType: string
  visible: string
  status: string
  perms: string
  iconType: string
  icon: string
}

export interface Menu extends BaseMenu {
  children?: BaseMenu[]
}

export interface Role {
  params?: any
  roleId: string
  roleName: string
  roleKey: string
  dataScope: string
  status: string
}

interface BaseDept {
  deptId: string | number
  parentId: string | number
  ancestors: string
  deptName: string
  leader: string
  phone: string
  email: string
  status: string
}

export interface Dept extends BaseDept {
  leaderName: string
  children?: BaseDept[]
}

export interface ConfigValue {
  dictLabel: string
  dictValue: string
}

export interface Config {
  configId: number
  configName: string
  configKey: string
  configValue: string
  configType: string
  remark: string
}

export interface User {
  userId: string
  deptId?: string | number
  deptName: string
  roleId: string
  userName: string
  password?: string
  nickName: string
  email: string
  phonenumber: string
  sex: string
  status: string
  loginIp: string
}
