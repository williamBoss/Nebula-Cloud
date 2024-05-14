import { HOME_URL, LOGIN_URL } from '@/config/config.js'

/**
 * staticRouter(静态路由)
 */
export const staticRouter = [
  {
    path: '/',
    redirect: LOGIN_URL
  },
  {
    path: LOGIN_URL,
    name: 'login',
    component: () => import('@/views/login/index.vue'),
    // 任何人都可访问的页面
    meta: { requiresAuth: false }
  },
  {
    path: '/layout',
    name: 'home',
    component: () => import('@/layout/index.vue'),
    redirect: HOME_URL,
    meta: { requiresAuth: true },
    children: [
      {
        path: '/home/index',
        name: 'homePage',
        component: () => import('@/views/home/index.vue'),
        meta: {
          requiresAuth: true,
          title: '首页',
          icon: 'HomeFilled',
          iconType: 'el',
          hidden: false
        }
      }
    ]
  },
  {
    path: '/chat',
    name: 'chat',
    component: () => import('@/views/chat/index.vue')
  }
]

/**
 * errorRouter(错误页面路由)
 */
export const errorRouter = [
  {
    path: '/403',
    name: '403',
    component: () => import('@/components/ErrorMessage/403.vue'),
    meta: {
      title: '403页面'
    }
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/components/ErrorMessage/404.vue'),
    meta: {
      title: '404页面'
    }
  },
  {
    path: '/500',
    name: '500',
    component: () => import('@/components/ErrorMessage/500.vue'),
    meta: {
      title: '500页面'
    }
  }
]

/**
 * notFoundRouter(找不到路由)
 */
export const notFoundRouter = {
  path: '/:pathMatch(.*)*',
  name: 'notFound',
  redirect: { name: '404' }
}
