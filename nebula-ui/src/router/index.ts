import { createRouter, createWebHistory } from 'vue-router'
import pinia, { globalStore } from '@/store/index.ts'
import { errorRouter, staticRouter } from '@/router/modules/staticRouter.js'
import { LOGIN_URL } from '@/config/config.ts'
import { initDynamicRouter } from '@/router/modules/dynamicRouter.ts'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...staticRouter, ...errorRouter]
})

const whiteList = [LOGIN_URL]

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
  // 判断当前用户是否已拉取完user_info信息
  if (Object.keys(global.userInfo).length === 0) {
    global
      .getUserInfo()
      .then((menus: any) => {
        console.log(111)
        // 拉取菜单
        initDynamicRouter(menus).then(() => {
          next({ ...to, replace: true })
        })
      })
      .catch((err) => {
        console.error(err)
        global.logOut().then(() => {
          router.replace(LOGIN_URL)
        })
      })
  } else {
    next()
  }
})

export default router
