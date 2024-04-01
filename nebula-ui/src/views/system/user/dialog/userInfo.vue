<template>
  <el-dialog
    :title="title"
    v-model="open"
    width="600px"
    append-to-body
  >
    <el-form
      ref="userFormRef"
      :model="userForm"
      :rules="rules"
      label-width="80px"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item
            v-if="userForm.userId === undefined"
            label="登录账号"
            prop="userName"
          >
            <el-input
              v-model="userForm.userName"
              placeholder="请输入登录账号"
              autocomplete="off"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            v-if="userForm.userId === undefined"
            label="用户密码"
            prop="password"
          >
            <el-input
              v-model="userForm.password"
              placeholder="请输入用户密码"
              type="password"
              autocomplete="new-password"
              show-password
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item
            label="用户名称"
            prop="nickName"
          >
            <el-input
              v-model="userForm.nickName"
              placeholder="请输入用户名称"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="角色"
            prop="roleId"
          >
            <el-select
              v-model="userForm.roleId"
              placeholder="请选择"
            >
              <el-option
                v-for="item in roleOptions"
                :key="item.roleId"
                :label="item.roleName"
                :value="item.roleId"
                :disabled="item.status === 1"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item
            label="手机号码"
            prop="phonenumber"
          >
            <el-input
              v-model="userForm.phonenumber"
              placeholder="请输入手机号码"
              maxlength="11"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态">
            <el-radio-group v-model="userForm.status">
              <el-radio
                v-for="dict in statusOptions"
                :key="dict.dictValue"
                :label="dict.dictValue"
                >{{ dict.dictLabel }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item
            label="部门"
            prop="deptId"
          >
            <el-tree-select
              v-model="userForm.deptId"
              :data="deptOptions"
              :props="{ value: 'deptId', label: 'deptName' }"
              check-strictly
              :render-after-expand="false"
              placeholder="请选择部门"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button
          type="primary"
          @click="submitForm(userFormRef)"
          >确 定
        </el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { ElMessage as elMessage, FormInstance } from 'element-plus'
import { resetForm } from '@/utils/util'
import { UserService } from '@/api/sys-api.js'
import { ConfigValue, Dept, User } from '@api/types/sys-api'

interface UserInfoProps {
  title: string
  sexOptions?: ConfigValue[]
  statusOptions?: ConfigValue[]
  deptOptions?: Dept[]
}

withDefaults(defineProps<UserInfoProps>(), {
  title: '题目'
})
const emit = defineEmits(['refresh'])
const open = ref(false)
const roleOptions = ref()
const userFormRef = ref()
// 表单参数
const userForm = ref<User>({} as User)
// 表单校验参数
const rules = reactive({
  userName: [{ required: true, message: '登陆账号不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '用户密码不能为空', trigger: 'blur' }],
  nickName: [{ required: true, message: '用户名称不能为空', trigger: 'blur' }],
  roleId: [{ required: true, message: '角色不能为空', trigger: 'blur' }],
  email: [
    {
      type: 'email',
      message: '请输入正确的邮箱地址',
      trigger: ['blur', 'change']
    }
  ],
  phonenumber: [
    {
      pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
      message: '请输入正确的手机号码',
      trigger: 'blur'
    }
  ]
})

// 接收父组件参数
const acceptParams = (data: any) => {
  roleOptions.value = data.roles
  if (data.data) {
    userForm.value = data.data
    if (userForm.value.deptId === 0) {
      delete userForm.value.deptId
    }
  }
  open.value = true
}
const submitForm = async (formEl: FormInstance) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      if (userForm.value.userId) {
        UserService.user.updateUser(userForm.value).then(() => {
          open.value = false
          elMessage.success('成功')
          emit('refresh')
        })
      } else {
        UserService.user.addUser(userForm.value).then(() => {
          open.value = false
          elMessage.success('成功')
          emit('refresh')
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
  resetForm(userFormRef.value)
  userForm.value = {} as User
}

defineExpose({
  acceptParams,
  reset
})
</script>

<style scoped></style>
