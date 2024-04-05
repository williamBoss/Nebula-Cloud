import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import pinia, { globalStore } from '@/store/index.ts'
import router, { buildRoutes } from '@/router/index.ts'
import SvgIcon from '~virtual/svg-component'

const app = createApp(App)
app.use(pinia)
initializeApp().catch((err) => {
  console.log('初始化应用失败', err)
})

/**
 * 初始化应用
 */
async function initializeApp() {
  await initializeUserInfoAndRouter()
  //初始化路由
  app.use(router)
  //初始化全局组件
  app.component(SvgIcon.name, SvgIcon)
  //挂载
  app.mount('#app')
}

/**
 * 初始化用户信息和路由
 */
async function initializeUserInfoAndRouter() {
  //获取登录用户信息
  if (globalStore().token) {
    await globalStore().getUserInfo()
    buildRoutes()
  }
  return Promise.resolve()
}
