import { LOGIN_URL } from '@/config/config.js'
import { notFoundRouter } from '@/router/modules/staticRouter.ts'
import router from '@/router/index.js'
import { Menu } from '@api/types/sys-api'
import { ElNotification } from 'element-plus'

/**
 * 初始化动态路由
 */
export const initDynamicRouter = async (menus: Menu[]) => {
  try {
    // 1.获取菜单列表 && 按钮权限 判断当前用户有没有菜单权限
    if (!menus.length) {
      ElNotification({
        title: '无权访问',
        message: '当前账号无任何菜单权限，请联系系统管理员！',
        type: 'warning',
        duration: 3000
      })
      await router.replace(LOGIN_URL)
      return Promise.reject(new Error('No permission'))
    }

    // 2.添加动态路由

    // 3.最后添加 notFoundRouter
    router.addRoute(notFoundRouter)
  } catch (error) {
    // 💢 当按钮 || 菜单请求出错时，重定向到登陆页
    await router.replace(LOGIN_URL)
    return Promise.reject(error)
  }
}
