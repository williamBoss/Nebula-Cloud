<script setup lang="ts">
import { defineComponent, reactive, ref } from 'vue'
import { ElMessage as elMessage, FormInstance, FormRules } from 'element-plus'
import { resetForm } from '@/utils/util'
import { Dept, User } from '@api/types/sys-api'
import { DeptService, UserService } from '@api/sys-api.ts'

defineComponent({
  name: 'DeptInfo'
})

interface Props {
  deptList: Array<Dept>
  title: string
}

withDefaults(defineProps<Props>(), {
  title: ''
})
const emit = defineEmits(['refresh'])
const open = ref(false)
const formRef = ref()
// 表单参数
const formData = ref<Dept>({} as Dept)
// 表单校验参数
const rules = reactive<FormRules<typeof formData>>({
  deptName: [{ required: true, message: '部门名称不能为空', trigger: 'blur' }],
  parentId: [{ required: true, message: '父部门不能为空', trigger: 'blur' }]
})
const userOptions = ref<User[]>()

// 接收父组件参数
const acceptParams = (data: Dept | undefined = undefined) => {
  reset()
  if (data) {
    formData.value = data
    UserService.user.getListByUserId(data.leader).then((data: User[]) => {
      userOptions.value = data
    })
  } else {
    formData.value.parentId = '100'
    formData.value.ancestors = '0,100'
  }
  open.value = true
}
const handleCheck = (data: Dept) => {
  formData.value.ancestors = data.ancestors + ',' + data.deptId
}
const remoteMethod = (query: string) => {
  UserService.user.getListByNickName(query).then((data: User[]) => {
    userOptions.value = data
  })
}
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return

  const success = () => {
    open.value = false
    elMessage.success('成功')
    emit('refresh')
  }

  await formEl.validate((valid, fields) => {
    if (valid) {
      if (formData.value.deptId) {
        DeptService.dept.update(formData.value).then(() => {
          success()
        })
      } else {
        DeptService.dept.add(formData.value).then(() => {
          success()
        })
      }
    } else {
      console.error('error submit!', fields)
    }
  })
}
const cancel = () => {
  open.value = false
  reset()
}
const reset = () => {
  resetForm(formRef.value)
  formData.value = {} as Dept
}

defineExpose({
  acceptParams,
  reset
})
</script>

<template>
  <el-dialog
    :title="title"
    v-model="open"
    width="520px"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="rules"
      label-width="80px"
    >
      <el-form-item
        label="部门名称"
        prop="deptName"
      >
        <el-input v-model="formData.deptName" />
      </el-form-item>
      <el-form-item
        v-if="formData.parentId !== '0'"
        label="上级部门"
        prop="parentId"
      >
        <el-tree-select
          v-model="formData.parentId"
          :data="deptList"
          :props="{ label: 'deptName' }"
          value-key="deptId"
          node-key="deptId"
          :render-after-expand="false"
          check-strictly
          show-checkbox
          check-on-click-node
          placeholder="请选择部门"
          @check="handleCheck"
        />
      </el-form-item>
      <el-form-item
        label="负责人"
        prop="leader"
      >
        <el-select
          v-model="formData.leader"
          remote
          :filterable="true"
          :remote-method="remoteMethod"
        >
          <el-option
            v-for="item in userOptions"
            :key="item.userId"
            :label="item.nickName"
            :value="item.userId"
          />
        </el-select>
      </el-form-item>
      <el-form-item
        label="联系电话"
        prop="phone"
      >
        <el-input v-model="formData.phone" />
      </el-form-item>
      <el-form-item
        label="邮箱"
        prop="email"
      >
        <el-input v-model="formData.email" />
      </el-form-item>
      <el-form-item
        label="状态"
        prop="status"
      >
        <el-radio-group v-model="formData.status">
          <el-radio label="0">启用</el-radio>
          <el-radio label="1">停用</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button
          type="primary"
          @click="submitForm(formRef)"
          >确 定
        </el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped></style>
