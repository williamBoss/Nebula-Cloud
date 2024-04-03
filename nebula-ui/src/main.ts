import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import pinia, { globalStore } from '@/store/index.ts'
import router, { buildRoutes } from '@/router/index.ts'
import SvgIcon from '~virtual/svg-component'

const app = createApp(App)
app.use(pinia).use(router)
app.component(SvgIcon.name, SvgIcon).mount('#app')

/**
 * 初始化应用
 */
const initializeApp = () => {
  //获取登录用户信息
  if (globalStore().token) {
    globalStore()
      .getUserInfo()
      .then(() => {
        //构建系统的路由
        buildRoutes()
      })
  }
}

initializeApp()
