import { RouteRecordRaw } from 'vue-router'
import { HOME_URL, LOGIN_URL } from '@/config/config.ts'

/**
 * staticRouter(静态路由)
 */
export const staticRouter: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: LOGIN_URL
  },
  {
    path: '/login',
    component: () => import('@/views/login/login.vue'),
    // 任何人都可访问的页面
    meta: { requiresAuth: false }
  },
  {
    path: '/patient',
    component: () => import('@/views/patient/patientRouter.vue'),
    // 任何人都可访问的页面
    meta: { requiresAuth: true },
    redirect: HOME_URL,
    children: [
      {
        path: '/home',
        component: () => import('@/views/patient/patient.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'info',
        component: () => import('@/views/patient/patientInfo.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'punchClock',
        component: () => import('@/views/patient/punchClock.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'visit',
        component: () => import('@/views/visit/index.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'educate',
        name: 'educate',
        component: () => import('@/views/educate/index.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'consult',
        name: 'consult',
        component: () => import('@/views/consult/index.vue'),
        meta: { requiresAuth: true }
      }
    ]
  },
  {
    path: '/visit',
    children: [
      {
        path: 'form',
        name: 'visitForm',
        component: () => import('@/views/visit/form.vue'),
        meta: { requiresAuth: true }
      }
    ]
  },
  {
    path: '/educate',
    children: [
      {
        path: 'detail',
        name: 'educateDetail',
        component: () => import('@/views/educate/detail.vue'),
        meta: { requiresAuth: true }
      }
    ]
  },
  {
    path: '/consult',
    children: [
      {
        path: 'form',
        name: 'consultForm',
        component: () => import('@/views/consult/form.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'detail',
        name: 'consultDetail',
        component: () => import('@/views/consult/detail.vue'),
        props: (route) => {
          return route.query
        },
        meta: { requiresAuth: true }
      }
    ]
  }
]

/**
 * errorRouter(错误页面路由)
 */
export const errorRouter = [
  /*{
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
                              }*/
]

/**
 * notFoundRouter(找不到路由)
 */
export const notFoundRouter = {
  path: '/:pathMatch(.*)*',
  name: 'notFound',
  redirect: { name: '404' }
}
