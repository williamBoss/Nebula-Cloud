<script setup lang="ts">
import { defineComponent, reactive, ref } from 'vue'
import { ElMessage as elMessage, FormInstance, FormRules } from 'element-plus'
import { resetForm } from '@/utils/util'
import { Config } from '@api/types/sys-api'
import { ConfigService } from '@api/sys-api.ts'

defineComponent({
  name: 'ConfigInfo'
})

interface Props {
  title: string
}

withDefaults(defineProps<Props>(), {
  title: ''
})
const emit = defineEmits(['refresh'])
const open = ref(false)
const formRef = ref()
// 表单参数
const formData = ref<Config>({} as Config)
// 表单校验参数
const rules = reactive<FormRules<typeof formData>>({
  configName: [{ required: true, message: '参数名称不能为空', trigger: 'blur' }],
  configKey: [{ required: true, message: '参数键名不能为空', trigger: 'blur' }],
  configValue: [
    { required: true, message: '参数键值不能为空', trigger: 'blur' },
    {
      validator: (_rule: any, value: any, callback: any) => {
        try {
          JSON.parse(value)
          callback()
        } catch (e) {
          callback('请输入正确的参数键值，格式为：json')
        }
      },
      trigger: 'blur'
    }
  ]
})

// 接收父组件参数
const acceptParams = (data: Config | undefined = undefined) => {
  reset()
  if (data) {
    formData.value = data
  }
  open.value = true
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
      if (formData.value.configId) {
        ConfigService.config.update(formData.value).then(() => {
          success()
        })
      } else {
        ConfigService.config.add(formData.value).then(() => {
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
  formData.value = {} as Config
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
        label="参数名称"
        prop="configName"
      >
        <el-input
          v-model="formData.configName"
          placeholder="请输入参数名称"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item
        label="参数键名"
        prop="configKey"
      >
        <el-input
          v-model="formData.configKey"
          :disabled="formData.configId"
          placeholder="请输入参数键名"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item
        label="参数键值"
        prop="configValue"
      >
        <el-input
          v-model="formData.configValue"
          placeholder="请输入参数键值"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item
        label="备注"
        prop="remark"
      >
        <el-input
          type="textarea"
          :rows="4"
          v-model="formData.remark"
          placeholder="请输入备注"
          autocomplete="off"
        />
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
