<script setup lang="ts">
import { defineComponent, reactive, ref } from 'vue'
import { ElMessage as elMessage, FormInstance, FormRules } from 'element-plus'
import { resetForm } from '@/utils/util'
import { Menu } from '@api/types/sys-api'
import { MenuService } from '@api/sys-api.ts'

defineComponent({
  name: 'MenuInfo'
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
const formData = ref<Menu>({} as Menu)
// 表单校验参数
const rules = reactive<FormRules<typeof formData>>({
  menuName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  menuType: [{ required: true, message: '请输入菜单类型', trigger: 'blur' }]
})
const menuList = ref<Menu[]>([])

// 接收父组件参数
const acceptParams = (data: Menu | undefined = undefined) => {
  reset()
  if (data) {
    data.parentId = data.parentId === '0' ? '' : data.parentId
    formData.value = data
  }
  MenuService.menu.getAllList().then((data: Menu[]) => {
    menuList.value = data
  })
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
      if (formData.value.parentId) {
        formData.value.parentId = '0'
      }
      if (formData.value.menuId) {
        MenuService.menu.update(formData.value).then(() => {
          success()
        })
      } else {
        MenuService.menu.add(formData.value).then(() => {
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
  formData.value = {} as Menu
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
    align-center
    width="600px"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="rules"
      label-width="80px"
    >
      <el-form-item
        label="菜单类型"
        prop="menuType"
      >
        <el-radio-group v-model="formData.menuType">
          <el-radio-button label="M">目录</el-radio-button>
          <el-radio-button label="C">菜单</el-radio-button>
          <el-radio-button label="F">按钮</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item
        label="上级目录"
        prop="parentId"
      >
        <el-tree-select
          v-model="formData.parentId"
          :data="menuList"
          :props="{ label: 'menuName' }"
          value-key="menuId"
          node-key="menuId"
          :render-after-expand="false"
          check-strictly
          check-on-click-node
          placeholder="请选择上级菜单"
        />
      </el-form-item>
      <el-form-item
        label="菜单名称"
        prop="menuName"
      >
        <el-input
          v-model="formData.menuName"
          placeholder="请输入菜单名称"
        />
      </el-form-item>
      <el-form-item
        label="图标类型"
        prop="iconType"
      >
        <el-radio-group v-model="formData.iconType">
          <el-radio-button label="el">el-icon</el-radio-button>
          <el-radio-button label="sl">自定义图标</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item
        label="图标"
        prop="icon"
      >
        <el-input
          v-model="formData.icon"
          placeholder="请输入图标"
        />
      </el-form-item>
      <el-form-item
        label="路径"
        prop="path"
      >
        <el-input
          v-model="formData.path"
          placeholder="请输入路径"
        />
      </el-form-item>
      <el-form-item
        label="组件"
        prop="component"
      >
        <el-input
          v-model="formData.component"
          placeholder="请输入组件"
        />
      </el-form-item>
      <el-form-item
        label="查询参数"
        prop="queryParam"
      >
        <el-input
          v-model="formData.queryParam"
          placeholder="请输入查询参数"
        />
      </el-form-item>
      <el-form-item
        label="是否可见"
        prop="visible"
      >
        <el-switch
          v-model="formData.visible"
          inline-prompt
          active-text="显示"
          active-value="0"
          inactive-text="隐藏"
          inactive-value="1"
        />
      </el-form-item>
      <el-form-item
        label="状态"
        prop="status"
      >
        <el-switch
          v-model="formData.status"
          inline-prompt
          active-text="正常"
          active-value="0"
          inactive-text="停用"
          inactive-value="1"
        />
      </el-form-item>
      <el-form-item
        label="权限标识"
        prop="perms"
      >
        <el-input
          v-model="formData.perms"
          placeholder="请输入权限标识"
        />
      </el-form-item>
      <el-form-item
        label="排序"
        prop="orderNum"
      >
        <el-input-number
          v-model="formData.orderNum"
          :min="1"
          controls-position="right"
          placeholder="请输入排序"
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
