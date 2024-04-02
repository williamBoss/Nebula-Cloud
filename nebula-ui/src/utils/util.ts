import pinyin from 'js-pinyin'
import { FormInstance } from 'element-plus'
import { Menu } from '@api/types/sys-api'

/**
 * @description 获取当前时间
 * @return
 */
export function getTimeState(): string {
  // 获取当前时间
  const timeNow = new Date()
  // 获取当前小时
  const hours = timeNow.getHours()
  // 判断当前时间段
  if (hours >= 6 && hours <= 10) return '早上好 ⛅'
  if (hours >= 10 && hours <= 14) return '中午好 🌞'
  if (hours >= 14 && hours <= 18) return '下午好 🌞'
  if (hours >= 18 && hours <= 24) return '晚上好 🌛'
  if (hours >= 0 && hours <= 6) return '凌晨好 🌛'
  return '未知时间段'
}

/**
 * 获取本地图片
 * @param {string} pathName assert下图片路径及名称
 * @return {string}
 */
export function getImageUrl(pathName: string) {
  return new URL(`../assets/${pathName}.jpg`, import.meta.url).href
}

/**
 * @description 表单重置
 * @param formEl
 */
export const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

/**
 * @description 添加日期范围
 * @param params
 * @param {string} propName
 * @return {*}
 */
export function addDateRange(params: any, propName?: string): any {
  const search = params
  search.params = {}
  const { dateRange } = search
  if (dateRange && '' !== dateRange) {
    if (!propName) {
      search.params['beginTime'] = dateRange[0]
      search.params['endTime'] = dateRange[1]
    } else {
      search.params['begin' + propName] = dateRange[0]
      search.params['end' + propName] = dateRange[1]
    }
    delete search.dateRange
  }
  return search
}

/**
 * 使用递归，过滤需要缓存的路由
 * @param menuList 所有菜单列表
 * @param cacheArr 缓存的路由菜单 name ['**','**']
 * @return
 */
export function getKeepAliveRouterName(menuList: any[], cacheArr: string[] = []): string[] {
  menuList.forEach((item) => {
    item.meta.noCache && item.name && cacheArr.push(item.name)
    item.children?.length && getKeepAliveRouterName(item.children, cacheArr)
  })
  return cacheArr
}

/**
 * @description 使用递归，过滤出需要渲染在左侧菜单的列表（剔除 isHide == true 的菜单）
 * @param menuList 所有菜单列表
 * @return
 * */
export function getShowMenuList(menuList: any[]): any[] {
  const newMenuList = JSON.parse(JSON.stringify(menuList))
  return newMenuList.filter((item: any) => {
    item.children?.length && (item.children = getShowMenuList(item.children))
    return !item?.hidden
  })
}

/**
 * @description 使用递归处理路由菜单 path，生成一维数组(第一版本地路由鉴权会用到)
 * @param {Array} menuList 所有菜单列表
 * @param {Array} menuPathArr 菜单地址的一维数组 ['**','**']
 * @return {Array}
 */
export function getMenuListPath(menuList: any[], menuPathArr: string[] = []): string[] {
  menuList.forEach((item) => {
    typeof item === 'object' && item.path && menuPathArr.push(item.path)
    item.children?.length && getMenuListPath(item.children, menuPathArr)
  })
  return menuPathArr
}

/**
 * @description 使用递归，过滤出当前路径匹配的面包屑地址
 * @param {String} path 当前访问地址
 * @param {Array} menuList 所有菜单列表
 * @return {Array}
 */
export function getCurrentBreadcrumb(path: string, menuList: any[]): any[] | undefined {
  const tempPath: any[] = []
  try {
    const getNodePath = (node: any) => {
      tempPath.push(node)
      if (node.path === path) throw new Error('Find IT!')
      if (node.children?.length) node.children.forEach((item: any) => getNodePath(item))
      tempPath.pop()
    }
    menuList.forEach((item) => getNodePath(item))
  } catch (e) {
    return tempPath
  }
}

/**
 * @description 递归处理菜单数据，生成符合路由菜单格式的数据
 * @param menu
 */
export function transformMenuItem(menu: Menu) {
  const transformMenu = {
    path: menu.path,
    name: menu.path.split('/').pop(),
    component: () => import(menu.component),
    meta: {
      requiresAuth: true,
      title: menu.menuName,
      icon: menu.icon,
      iconType: menu.iconType,
      hidden: menu.visible === '1'
    },
    children: [] as any[]
  }
  if (menu.children && menu.children.length > 0) {
    transformMenu.children = menu.children.map((child) => transformMenuItem(child))
  }
  return transformMenu
}

/**
 * @description 双重递归找出所有面包屑存储到 pinia/vuex 中
 * @param {Array} menuList 所有菜单列表
 * @return {Object}
 */
export function getAllBreadcrumbList(menuList: any): any {
  const handleBreadcrumbList: any = {}
  const loop = (menuItem: any) => {
    if (menuItem?.children?.length) menuItem.children.forEach((item: any) => loop(item))
    else handleBreadcrumbList[menuItem.path] = getCurrentBreadcrumb(menuItem.path, menuList)
  }
  menuList.forEach((item: any) => loop(item))
  return handleBreadcrumbList
}

/**
 * @description 扁平化数组对象(主要用来处理路由菜单)
 * @param {Array} menuList 所有菜单列表
 * @return {Array}
 */
export function getFlatArr(menuList: any[]): any[] {
  menuList.filter((route) => {
    if (route.children) {
      route.children = filterChildren(route.children)
    }
    return true
  })
  return menuList.reduce((pre, current) => {
    let flatArr = [...pre, current]
    if (current.children) {
      flatArr = [...flatArr, ...getFlatArr(current.children)]
    }
    return flatArr
  }, [])
}

/**
 * 过滤展平子节点
 * @param {Array} childrenMap
 * @return {*[]}
 */
function filterChildren(childrenMap: any[]): any[] {
  let children: any[] = []
  childrenMap.forEach((el) => {
    if (!new RegExp('^/').test(el.path)) el.path = '/'.concat(el.path)
    if (el.children && el.children.length) {
      if (el.component === 'ParentView') {
        el.children.forEach((c: any) => {
          c.path = el.path + '/' + c.path
          if (c.children && c.children.length) {
            children = children.concat(filterChildren(c.children))
            return
          }
          children.push(c)
        })
        return
      }
    }
    children = children.concat(el)
  })
  return children
}

/**
 * tree扁平化
 *
 * @param  tree
 * @param  children
 * @return
 */
export function treeToArray(tree: any[], children: string): any {
  return tree.reduce((pre: any[], current) => {
    let flatArr: any[] = [...pre, current]
    if (current[children]) {
      flatArr = [...flatArr, ...treeToArray(current[children], children)]
    }
    return flatArr
  }, [])
}

/**
 * 随机数
 * @return {number}
 */
export const generateId = (): number => {
  return Math.floor(Math.random() * 100000 + Math.random() * 20000 + Math.random() * 5000)
}

/**
 * 获取字符串的拼音首字母
 * @param {string} char
 * @return {*|string[]}
 */
export const getPinyinInitials = (char: string): any | string[] => {
  pinyin.setOptions({ checkPolyphone: false, charCase: 0 })
  return pinyin.getCamelChars(char.charAt(0))
}
