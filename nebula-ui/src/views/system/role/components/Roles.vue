<script setup lang="ts">
import { defineComponent, onMounted, ref } from 'vue'
import { Delete, Edit } from '@element-plus/icons-vue'
import { Role } from '@api/types/sys-api'
import RoleDialog from '@/views/system/role/components/RoleDialog.vue'
import { RoleService } from '@api/sys-api.ts'
import { ElMessage } from 'element-plus'

defineComponent({
  name: 'Roles'
})

interface Props {
  defaultRoleKey?: string
}

type Emits = {
  (e: 'select-role', value: string): string
}

const props = withDefaults(defineProps<Props>(), {
  defaultRoleKey: 'superadmin'
})
const emits = defineEmits<Emits>()
const roleList = ref<Role[]>()
const roleDialog = ref<InstanceType<typeof RoleDialog> | null>()
const dialogTitle = ref<string>('')

const getRoleList = () => {
  RoleService.role.getAll().then((data: Role[]) => {
    roleList.value = data
  })
}
const handleSelectRole = (index: string) => {
  emits('select-role', index)
}
const addRole = () => {
  dialogTitle.value = '添加角色'
  roleDialog.value?.toggleDialog(true)
}
const editRole = (id: string) => {
  dialogTitle.value = '修改角色'
  roleDialog.value?.toggleDialog(true, id)
}
const deleteRole = (id: string) => {
  RoleService.role.del(id).then(() => {
    getRoleList()
  })
}
const saveRole = (role: Role) => {
  const promise = role.roleId ? RoleService.role.updateRole(role) : RoleService.role.addRole(role)
  promise.then(() => {
    roleDialog.value?.toggleDialog(false)
    ElMessage.success('保存成功')
    getRoleList()
  })
}

onMounted(() => {
  getRoleList()
})
</script>

<template>
  <div style="height: 100%">
    <el-card
      class="card rounded is-plain"
      shadow="never"
      :body-style="{ padding: '0' }"
      style="height: 100%"
    >
      <template #header>
        <div class="flx flx-justify-between">
          <div class="title">角色列表</div>
          <div>
            <el-button
              type="primary"
              size="small"
              @click="addRole"
            >
              添加
            </el-button>
          </div>
        </div>
      </template>
      <el-menu
        :default-active="props.defaultRoleKey"
        @select="handleSelectRole"
      >
        <el-menu-item
          class="role-menu-item"
          v-for="role in roleList"
          :index="role.roleKey"
        >
          <div
            class="flx-justify-between"
            style="width: 100%"
          >
            <span>{{ role.roleName }}</span>
            <div v-if="role.roleId !== '1'">
              <el-tooltip
                effect="dark"
                content="修改"
                placement="top"
              >
                <el-button
                  type="info"
                  link
                  @click="editRole(role.roleId)"
                >
                  <el-icon :size="15">
                    <Edit />
                  </el-icon>
                </el-button>
              </el-tooltip>
              <el-popconfirm
                title="确认删除该角色?"
                @confirm="deleteRole(role.roleId)"
              >
                <template #reference>
                  <span>
                    <el-tooltip
                      effect="dark"
                      content="删除"
                      placement="top"
                    >
                      <el-button
                        type="danger"
                        link
                      >
                        <el-icon :size="15">
                          <Delete />
                        </el-icon>
                      </el-button>
                    </el-tooltip>
                  </span>
                </template>
              </el-popconfirm>
            </div>
          </div>
        </el-menu-item>
      </el-menu>
    </el-card>
    <role-dialog
      ref="roleDialog"
      :title="dialogTitle"
      @submitForm="saveRole"
    />
  </div>
</template>

<style scoped>
.role-menu-item {
  height: 40px;
  line-height: 40px;
  padding-left: var(--size-3);
  padding-right: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-inline: 4px;
  margin-block: 4px;
  border-radius: var(--radius-2);
}

.role-menu-item:hover {
  background-color: #efeffa;
}

.role-menu-item.is-active {
  background-color: #e0e0f6;
}

.role-menu-item .el-icon {
  margin-right: 0;
}
</style>
