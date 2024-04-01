<script setup lang="ts">
import { defineComponent, ref } from 'vue'
import DynamicTable from '@components/Table/DynamicTable.vue'
import { ColumnExtProp } from '@components/Table/types/column-props.ts'
import { User } from '@api/types/sys-api'
import { RoleService, UserService } from '@api/sys-api.ts'

defineComponent({
  name: 'MemberRoleDialog'
})

interface Props {
  roleKey: string
}

type Emits = {
  (e: 'refresh'): void
}

const props = defineProps<Props>()
const emits = defineEmits<Emits>()
const visible = ref<boolean>(false)
const tableColumns: ColumnExtProp[] = [
  {
    prop: 'nickName',
    label: '用户名称'
  },
  {
    prop: 'sex',
    label: '性别'
  },
  {
    prop: 'phonenumber',
    label: '手机号'
  }
]
const dataList = ref<User[]>([])
const searchParam = ref<string>('')

const toggleDialog = (visibleStatus: boolean) => {
  getUserList()
  visible.value = visibleStatus
}
const getUserList = () => {
  UserService.user.getListByNickNameToRole(searchParam.value).then((data: User[]) => {
    dataList.value = data
  })
}
const handleAddMember = (userId: string | number) => {
  RoleService.role.addUserRole(props.roleKey, userId).then(() => {
    getUserList()
    emits('refresh')
  })
}

defineExpose({
  toggleDialog
})
</script>

<template>
  <el-dialog
    v-model="visible"
    title="添加用户"
    width="800"
  >
    <dynamic-table
      :data="dataList"
      :columns="tableColumns"
      :hidden-pagination="true"
      @query-list="getUserList"
    >
      <template #tableHeader>
        <div>
          <el-input
            v-model="searchParam"
            placeholder="姓名"
            clearable
            style="width: 220px"
          />
          <el-button
            type="primary"
            style="margin-left: var(--size-2)"
            @click="getUserList"
          >
            搜 索
          </el-button>
        </div>
      </template>
      <template #operationColumn="{ row }">
        <el-button
          type="primary"
          size="small"
          @click="handleAddMember(row.userId)"
        >
          添加
        </el-button>
      </template>
    </dynamic-table>
  </el-dialog>
</template>

<style scoped></style>
