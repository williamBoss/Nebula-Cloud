import { Request } from '@/request/request.js'
import { Config, Dept, Menu, Pageable, Role, User } from '@api/types/sys-api'

const system = `system`

/**
 * 系统基础请求
 */
export class LoginService {
  private static BASE_AUTH_URL = `/auth`

  static auth = {
    getCaptcha: () => Request.get(`${this.BASE_AUTH_URL}/captcha`),

    login: (username: string | number, password: string | number, captcha: string | number, uuid: string | number) =>
      Request.post(`${this.BASE_AUTH_URL}/login`, { username, password, captcha, uuid }),

    logout: () => Request.delete(`${this.BASE_AUTH_URL}/logout`)
  }
}

export class MenuService {
  private static BASE_MENU_URL = `${system}/sysMenu`

  static menu = {
    getList: (searchParam: string) =>
      Request.get<Pageable<Menu>>(`${this.BASE_MENU_URL}/list`, { searchKey: searchParam }),

    getAllList: () => Request.get<Menu[]>(`${this.BASE_MENU_URL}/all/list`),

    getInfoById: (id: string | number) => Request.get<Menu>(`${this.BASE_MENU_URL}/${id}`),

    add: (data: {}) => Request.post(`${this.BASE_MENU_URL}`, data),

    update: (data: {}) => Request.put(`${this.BASE_MENU_URL}`, data),

    del: (id: string | number) => Request.delete(`${this.BASE_MENU_URL}/${id}`)
  }
}

/**
 * 查询字典
 */
export class ConfigService {
  private static BASE_CONFIG_URL = `${system}/sysConfig`

  static config = {
    getList: (searchParam: string) =>
      Request.get<Pageable<Config>>(`${this.BASE_CONFIG_URL}/list`, { searchKey: searchParam }),

    getInfoById: (id: string | number) => Request.get<Config>(`${this.BASE_CONFIG_URL}/${id}`),

    add: (data: {}) => Request.post(`${this.BASE_CONFIG_URL}`, data),

    update: (data: {}) => Request.put(`${this.BASE_CONFIG_URL}`, data),

    del: (id: string | number) => Request.delete(`${this.BASE_CONFIG_URL}/${id}`),

    // 根据字典类型查询字典数据信息
    getConfigByKey: (configKey: string) => Request.get<Config>(`${this.BASE_CONFIG_URL}/configKey/${configKey}`)
  }
}

/**
 * 查询部门
 */
export class DeptService {
  private static BASE_DEPT_URL = `${system}/sysDept`

  static dept = {
    getList: (searchParam: string) => Request.get<Dept[]>(`${this.BASE_DEPT_URL}/list`, { searchKey: searchParam }),

    getInfoById: (deptId: string | number) => Request.get<Dept>(`${this.BASE_DEPT_URL}/${deptId}`),

    add: (data: {}) => Request.post(`${this.BASE_DEPT_URL}`, data),

    update: (data: {}) => Request.put(`${this.BASE_DEPT_URL}`, data),

    del: (id: string | number) => Request.delete(`${this.BASE_DEPT_URL}/${id}`),

    getDeptTreeSelect: () => Request.get<Dept[]>(`${this.BASE_DEPT_URL}/treeselect`)
  }
}

export class RoleService {
  private static BASE_ROLE_URL = `${system}/sysRole`

  static role = {
    getAll: () => Request.get<Role[]>(`${this.BASE_ROLE_URL}/all`),

    getInfoById: (roleId: string | number) => Request.get<Role>(`${this.BASE_ROLE_URL}/${roleId}`),

    addRole: (data: {}) => Request.post(`${this.BASE_ROLE_URL}`, data),

    updateRole: (data: {}) => Request.put(`${this.BASE_ROLE_URL}`, data),

    del: (id: string | number) => Request.delete(`${this.BASE_ROLE_URL}/${id}`),

    getUserList: (roleKey: string, searchParam: string) =>
      Request.get<Pageable<User>>(`${this.BASE_ROLE_URL}/user/list/${roleKey}`, { searchKey: searchParam }),

    addUserRole: (roleKey: string | number, userId: string | number) =>
      Request.post(`${this.BASE_ROLE_URL}/addUserRole/${roleKey}/${userId}`),

    delUserRole: (roleKey: string | number, userId: string | number) =>
      Request.delete(`${this.BASE_ROLE_URL}/removeUserRole/${roleKey}/${userId}`),

    getCheckMenuByRole: (roleKey: string) => Request.get<string[]>(`${this.BASE_ROLE_URL}/menu/list/${roleKey}`),

    addMenuRole: (roleKey: string | number, menuIds: (string | number)[]) =>
      Request.post(`${this.BASE_ROLE_URL}/addRoleMenu/${roleKey}`, menuIds)
  }
}

/**
 * @description -封装User类型的接口方法
 */
export class UserService {
  private static BASE_USER_URL = `${system}/sysUser`

  static user = {
    getInfo: () => Request.get<User>(`${this.BASE_USER_URL}/getInfo`),

    getInfoById: (userId: string | number) => Request.get<User>(`${this.BASE_USER_URL}/${userId}`),

    getList: (query: {}) => Request.get<Pageable<User>>(`${this.BASE_USER_URL}/list`, query),

    getListByNickName: (nickName: string) => Request.get<User[]>(`${this.BASE_USER_URL}/list/nickName/${nickName}`),

    getListByUserId: (userId: string) => Request.get<User[]>(`${this.BASE_USER_URL}/list/userId/${userId}`),

    getListByNickNameToRole: (nickName: string) =>
      Request.get<User[]>(`${this.BASE_USER_URL}/list/role/nickName/${nickName}`),

    addUser: (data: {}) => Request.post(`${this.BASE_USER_URL}`, data),

    updateUser: (data: {}) => Request.put(`${this.BASE_USER_URL}`, data),

    del: (userId: string | number) => Request.delete(`${this.BASE_USER_URL}/${userId}`),

    resetUserPwd: (userId: string | number, password: string | number) =>
      Request.put(`${this.BASE_USER_URL}/resetPwd`, { userId, password }),

    changeUserStatus: (params: {}) => Request.put(`${this.BASE_USER_URL}/changeStatus`, params)
  }
}
