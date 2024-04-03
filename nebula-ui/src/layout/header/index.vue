<template>
  <div class="header">
    <div class="header-left">
      <div class="header-tools">
        <div
          class="header-tool"
          @click="toggle"
        >
          <el-icon
            v-if="!sidebarCollapse"
            :size="18"
          >
            <Fold />
          </el-icon>
          <el-icon
            v-else
            :size="18"
          >
            <Expand />
          </el-icon>
        </div>
        <Breadcrumb />
      </div>
    </div>
    <div class="header-right">
      <div class="header-tools">
        <!-- 退出登录 -->
        <el-dropdown trigger="click">
          <div class="header-tool avatar">
            <el-avatar :src="avatar === '' ? defaultAvatar : avatar"></el-avatar>
            <span class="nickName">
              {{ user.nickName }}
            </span>
            <el-icon>
              <caret-bottom />
            </el-icon>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item :icon="User">个人资料</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <div
          class="header-tool"
          @click="signOut"
        >
          <el-tooltip
            effect="dark"
            content="用户退出"
          >
            <el-icon :size="18">
              <svg-icon name="icon-exit" />
            </el-icon>
          </el-tooltip>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { CaretBottom, Expand, Fold, User } from '@element-plus/icons-vue'
import { userStore } from '@/store/modules/user.ts'
import defaultAvatar from '@/assets/images/profile.jpg'
import { defineComponent } from 'vue'
import Breadcrumb from '@components/Breadcrumb/index.vue'
import { globalStore } from '@/store'
import { useRouter } from 'vue-router'

defineComponent({
  name: 'PageHeader'
})

const props = defineProps({
  sidebarCollapse: {
    type: Boolean,
    default: false
  }
})
const emit = defineEmits(['update:sidebarCollapse'])
const router = useRouter()
const global = globalStore()
// 用户信息变量
const user = userStore()
const { avatar } = user

// ↓sidebar折叠/展开的开关
const toggle = () => {
  // ↓修改父组件值
  emit('update:sidebarCollapse', !props.sidebarCollapse)
}

// ↓登出
const signOut = () => {
  global.logOut().then(() => {
    router.push('/login')
  })
}
</script>

<style scoped>
.header {
  height: 54px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #ffffff;
}

.header .header-tools {
  height: 100%;
  flex-shrink: 0;
  padding: 0 12px;
  display: flex;
  align-items: center;
  box-sizing: border-box;
}

.header .header-tools .header-tool {
  height: 38px;
  padding: 0 12px;
  display: flex;
  align-items: center;
  border-radius: 6px;
  transition:
    color 0.2s,
    background-color 0.2s;
  box-sizing: border-box;
  cursor: pointer;
}

.header .header-tools .header-tool:hover {
  background: #f5f5f5;
  cursor: pointer;
}

.header .avatar {
  display: inline-flex;
  align-items: center;
}

.header .avatar .nickName {
  height: 14px;
  font-size: 14px;
  font-weight: 500;
  color: #272944;
  line-height: 14px;
  margin-right: 8px;
}

.header .avatar .el-avatar {
  width: 24px;
  height: 24px;
  margin-right: 8px;
}
</style>
