import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import pinia from '@/store/index.ts'
import router from '@/router/index.ts'
import SvgIcon from '~virtual/svg-component'

const app = createApp(App)
app.use(pinia).use(router)
app.component(SvgIcon.name, SvgIcon).mount('#app')
