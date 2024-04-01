<script setup lang="ts">
import { defineComponent, reactive, ref } from 'vue'
import { RoleService } from '@api/sys-api.ts'
import { FormInstance, FormRules } from 'element-plus'
import { Role } from '@api/types/sys-api'

defineComponent({
  name: 'RoleDialog'
})

interface Props {
  title: string
}

type Emits = {
  (e: 'submit-form', value: Role): void
}

const props = defineProps<Props>()
const emits = defineEmits<Emits>()
const visible = ref<boolean>(false)
const roleFormRef = ref<FormInstance>()
const roleForm = ref<Role>({} as Role)
const roleRules = reactive<FormRules<Role>>({
  roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
  roleKey: [
    { required: true, message: '请输入角色编码', trigger: 'blur' },
    { pattern: /^[A-Za-z0-9]+$/, message: '只能输入英文字符和数字', trigger: ['blur', 'change'] }
  ]
})

const toggleDialog = (visibleStatus: boolean, id: string | undefined = undefined) => {
  roleFormRef.value?.resetFields()
  roleForm.value = {} as Role
  visible.value = visibleStatus
  id &&
    RoleService.role.getInfoById(id).then((data: Role) => {
      roleForm.value = data
    })
}
const handleSubmit = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    valid && emits('submit-form', roleForm.value)
  })
}

defineExpose({
  toggleDialog
})
</script>

<template>
  <el-dialog
    v-model="visible"
    :title="props.title"
    width="500"
  >
    <el-form
      ref="roleFormRef"
      :model="roleForm"
      :rules="roleRules"
      @submit.prevent="handleSubmit"
    >
      <el-form-item
        label="角色名称"
        prop="roleName"
      >
        <el-input
          v-model="roleForm.roleName"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item
        label="角色编码"
        prop="roleKey"
      >
        <el-input
          v-model="roleForm.roleKey"
          autocomplete="off"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="visible = false">取消</el-button>
        <el-button
          type="primary"
          @click="handleSubmit(roleFormRef)"
        >
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped></style>
