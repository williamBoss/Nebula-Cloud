<template>
  <el-container>
    <el-aside :width="sidebarCollapse ? '65px' : '210px'">
      <Sidebar />
    </el-aside>
    <el-container>
      <el-header>
        <Header v-model:sidebar-collapse="sidebarCollapse" />
      </el-header>
      <el-scrollbar>
        <el-main>
          <router-view v-slot="{ Component }">
            <transition
              name="fade"
              mode="out-in"
              appear
            >
              <component :is="Component" />
            </transition>
          </router-view>
        </el-main>
      </el-scrollbar>
      <el-footer>
        <Footer />
      </el-footer>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { defineComponent, provide, ref } from 'vue'
import Sidebar from '@/layout/sidebar/index.vue'
import Header from '@/layout/header/index.vue'
import Footer from '@/layout/footer/index.vue'

defineComponent({
  name: 'PageLayout'
})

// ↓侧边栏折叠
const sidebarCollapse = ref(false)
// ↓提供给sidebar注入
provide('sidebarCollapse', sidebarCollapse)
</script>

<style scoped>
.el-aside {
  background-color: #ffffff;
  transition: width 0.25s;
  -webkit-transition: width 0.25s;
  -moz-transition: width 0.25s;
  -o-transition: width 0.25s;
}

.el-header {
  padding: 0;
}

.el-main {
  height: calc(100vh - 100px);
  box-sizing: border-box;
  overflow-x: hidden;
}

.el-footer {
  height: 30px;
  padding: 0;
}
</style>
