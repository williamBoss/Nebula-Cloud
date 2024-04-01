import { createRouter, createWebHistory } from 'vue-router'
import pinia, { globalStore } from '@/store/index.ts'
import { ElMessage as elMessage } from 'element-plus'
import { errorRouter, staticRouter } from '@/router/modules/staticRouter.js'
import { LOGIN_URL } from '@/config/config.ts'
import { initDynamicRouter } from '@/router/modules/dynamicRouter.js'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...staticRouter, ...errorRouter]
})

const whiteList = [LOGIN_URL]

router.beforeEach((to, _from, next) => {
  const global = globalStore(pinia)
  const fullPath = to.fullPath
  next()
  if (whiteList.indexOf(String(to.path)) !== -1 || !to.meta.requiresAuth) {
    // 在免登录白名单，直接进入
    next()
    return
  }
  const { token } = global
  if (to.meta.requiresAuth && !token) {
    // 保存当前路由地址，授权后还会跳到此地址
    sessionStorage.setItem('redirect_url', fullPath)
    // 请求授权,并跳转到路由
    next({
      path: '/login',
      // 保存我们所在的位置，以便以后再来
      query: { redirect: fullPath }
    })
  } else if (to.meta.requiresAuth) {
    // 判断当前用户是否已拉取完user_info信息
    if (global.userInfo || Object.keys(global.userInfo).length !== 0) {
      next()
      return
    }
    global
      .getUserInfo()
      .then(() => {
        // 拉取菜单
        initDynamicRouter().then(() => {
          next({ ...to, replace: true })
        })
      })
      .catch((err) => {
        global.logOut().then(() => {
          elMessage.error(err)
          next({ path: '/' })
        })
      })
  } else {
    next()
  }
})

export default router
