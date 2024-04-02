import { LOGIN_URL } from '@/config/config.js'
import { ElNotification as elNotification } from 'element-plus'
import { notFoundRouter } from '@/router/modules/staticRouter.ts'
import router from '@/router/index.js'
import { authStore } from '@/store/modules/auth.ts'

/**
 * 初始化动态路由
 */
export const initDynamicRouter = async () => {
  try {
    // 1.获取菜单列表 && 按钮权限
    const auth = authStore()

    // 2.判断当前用户有没有菜单权限
    if (!auth.authMenuList.length) {
      elNotification({
        title: '无权访问',
        message: '当前账号无任何菜单权限，请联系系统管理员！',
        type: 'warning',
        duration: 3000
      })
      await router.replace(LOGIN_URL)
      return Promise.reject(new Error('No permission'))
    }

    // 3.添加动态路由 (getFlatArr 方法把菜单全部处理成一维数组，方便添加动态路由)

    // 4.最后添加 notFoundRouter
    router.addRoute(notFoundRouter)
  } catch (error) {
    // 💢 当按钮 || 菜单请求出错时，重定向到登陆页
    await router.replace(LOGIN_URL)
    return Promise.reject(error)
  }
}
