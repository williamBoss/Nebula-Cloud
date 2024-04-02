<template>
  <div
    class="sidebar"
    :style="{ width: sidebarCollapse ? '65px' : '210px' }"
  >
    <div class="logo flx-center">
      <img
        v-if="headerLogo"
        class="logo-img"
        :src="headerLogo"
        alt="logo"
      />
      <span
        v-if="!sidebarCollapse"
        class="logo-text"
        >{{ title }}</span
      >
      <img
        v-else
        src="@/assets/images/logo/55x55.png"
        alt="logo"
      />
    </div>
    <el-scrollbar>
      <el-menu
        :default-active="defaultActiveMenu"
        background-color="#4949C9"
        text-color="#fff"
        :collapse="sidebarCollapse"
        :collapse-transition="false"
      >
        <SubMenu :menu-list="menu!.children" />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup lang="ts">
import { computed, defineComponent, inject, ref } from 'vue'
import SubMenu from '@/layout/sidebar/subMenu/index.vue'
import { staticRouter } from '@/router/modules/staticRouter'
import { HOME_ROUTER_NAME } from '@/config/config.js'
import { useRoute } from 'vue-router'
import { authStore } from '@/store/modules/auth.ts'

defineComponent({
  name: 'SidebarMenu'
})

const route = useRoute()
// ↓注入父组件值
const sidebarCollapse = ref(inject('sidebarCollapse'))
const defaultActiveMenu = computed(() => {
  const name = route.name
  return name || HOME_ROUTER_NAME
})

const title = ref(import.meta.env.VITE_APP_TITLE)
const headerLogo = ref(import.meta.env.VITE_APP_HEADER_LOGO)
const auth = authStore()
const menu = computed(() => {
  const router = staticRouter.find((e) => e.name === 'home')
  router.children = auth.showMenuListGet()
  return router
})
</script>

<style scoped>
.sidebar {
  height: 100vh;
  background: #4949c9;
  display: flex;
  flex-direction: column;
  transition: width 0.3s ease;
}

.sidebar .logo {
  box-sizing: border-box;
  height: 55px;
}

.sidebar .logo .logo-img {
  width: 28px;
  object-fit: contain;
  margin-right: 6px;
}

.sidebar .logo .logo-text {
  font-size: 21.5px;
  font-weight: bold;
  color: #ffffff;
  white-space: nowrap;
}

.sidebar .el-scrollbar {
  height: calc(100vh - 55px);
}

.sidebar .el-scrollbar .el-menu {
  width: 100%;
  overflow-x: hidden;
  border-right: none;
  padding-top: 20px;
  padding-bottom: 20px;
  padding-left: 10px;
}
</style>
