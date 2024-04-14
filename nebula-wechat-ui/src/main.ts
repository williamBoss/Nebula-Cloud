import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import pinia from '@/store/index.ts'
import router from '@/router/index.ts'
import SvgIcon from '~virtual/svg-component'
import 'vant/lib/index.css'

const app = createApp(App)
app.use(pinia)
//初始化路由
app.use(router)
//初始化全局组件
app.component(SvgIcon.name, SvgIcon)
//挂载
app.mount('#app')
