<script setup lang="ts">
import Members from '@/views/system/role/components/Members.vue'
import MenuPermissions from '@/views/system/role/components/MenuPermissions.vue'
import { ref } from 'vue'
import Roles from '@/views/system/role/components/Roles.vue'

const roleKey = ref<string>('superadmin')

const handleSelectRole = (index: string) => {
  roleKey.value = index
}
</script>

<template>
  <div
    class="flx"
    style="height: 100%"
  >
    <div class="left-panel">
      <roles
        :default-role-key="roleKey"
        @select-role="handleSelectRole"
      />
    </div>
    <div class="right-panel">
      <el-card
        class="card rounded is-plain"
        shadow="never"
        style="height: 100%"
      >
        <el-tabs>
          <el-tab-pane label="角色-用户列表">
            <members :role-key="roleKey" />
          </el-tab-pane>
          <el-tab-pane label="角色-功能权限">
            <menu-permissions :role-key="roleKey" />
          </el-tab-pane>
        </el-tabs>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
.left-panel {
  width: calc(220px + var(--size-2));
  margin-right: var(--size-2);
  flex-grow: 1;
}

.right-panel {
  width: calc(100% - calc(220px + var(--size-2)));
}
</style>
