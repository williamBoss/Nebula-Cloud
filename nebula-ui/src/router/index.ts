import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import pinia, { globalStore } from '@/store/index.ts'
import { errorRouter, notFoundRouter, staticRouter } from '@/router/modules/staticRouter.js'
import { LOGIN_URL } from '@/config/config.ts'
import { userStore } from '@/store/modules/user.ts'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...staticRouter, ...errorRouter]
})

const whiteList = [LOGIN_URL, '/chat']

router.beforeEach((to, _from, next) => {
  const global = globalStore(pinia)
  const fullPath = to.fullPath
  if (whiteList.indexOf(String(to.path)) !== -1) {
    // 在免登录白名单，直接进入
    return next()
  }
  const { token } = global
  // 判断token是否存在
  if (!token) {
    // 保存当前路由地址，授权后还会跳到此地址
    sessionStorage.setItem('redirect_url', fullPath)
    // 请求授权,并跳转到路由
    return next({
      path: '/login',
      // 保存我们所在的位置，以便以后再来
      query: { redirect: fullPath }
    })
  }
  next()
})

export default router

/**
 * 构建路由
 * @param menuRouterList
 */
export function buildRoutes(menuRouterList: any | undefined = undefined) {
  let menuList = menuRouterList ? menuRouterList : userStore().menuRouterList || []

  /**
   * 1、构建整个路由信息
   * 2、添加到路由里
   */
  // 获取所有vue组件引用地址 用于构建路由
  const modules = import.meta.glob('../views/**/**.vue')
  // 获取所有vue组件 用于注入name属性 name属性用于keep-alive

  //1、构建整个路由信息
  menuList
    .filter((e: any) => e.menuId || e.path)
    .map((e: any) => {
      let route: RouteRecordRaw = transformRouter(e, modules)
      //2、添加到路由里
      router.addRoute('home', route)
    })

  // 3. 添加404路由
  router.addRoute(notFoundRouter)
}

/**
 * @description 递归处理菜单数据，生成符合路由菜单格式的数据
 * @param e 当前菜单对象
 * @param modules vue组件引用地址
 */
export function transformRouter(e: any, modules: any) {
  let route: any = {
    path: e.path.startsWith('/') ? e.path : `/${e.path}`,
    name: e.path.split('/').pop(),
    component:
      modules[`../views/${e.component && e.component.startsWith('/') ? e.component.slice(1) : e.component}.vue`],
    meta: {
      requiresAuth: true,
      title: e.menuName,
      icon: e.icon,
      iconType: e.iconType,
      hidden: e.visible === '1'
    },
    children: [] as any[]
  }
  if (e.children && e.children.length > 0) {
    e.children = e.children.map((child: any) => transformRouter(child, modules))
  }
  return route
}
