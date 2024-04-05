<script setup lang="ts">
import DynamicTable from '@components/Table/DynamicTable.vue'
import { computed, defineComponent, onMounted, ref, watch } from 'vue'
import { ColumnExtProp } from '@components/Table/types/column-props.ts'
import { ConfigService, RoleService } from '@api/sys-api.ts'
import { Config, ConfigValue, Pageable, User } from '@api/types/sys-api'
import { Delete } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import MemberRoleDialog from '@/views/system/role/components/MemberRoleDialog.vue'

defineComponent({
  name: 'Member'
})

interface Props {
  roleKey: string
}

const props = defineProps<Props>()
const tableColumns = ref<Array<ColumnExtProp>>([
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
  },
  {
    prop: 'deptName',
    label: '部门名称'
  }
])
const pageable = ref<Pageable<User>>({ pageNumber: '1', pageSize: '10', totalRow: '0', records: [] })
const searchParam = ref<string>('')
const dictSex = ref<ConfigValue[]>()
const roleKey = computed(() => props.roleKey)
const memberRoleDialogRef = ref<InstanceType<typeof MemberRoleDialog>>()

const transform = (row: User) => {
  const sex = dictSex.value?.find((item) => item.dictValue == row.sex)
  return sex ? sex.dictLabel : ''
}
const getUserList = () => {
  const { records, ...pageParam } = pageable.value
  RoleService.role
    .getUserList(roleKey.value, Object.assign({}, { searchKey: searchParam.value }, pageParam))
    .then((data: Pageable<User>) => {
      pageable.value = data
    })
}
const handleSizeChange = (val: string) => {
  pageable.value.pageNumber = '1'
  pageable.value.pageSize = val
  getUserList()
}
const handleCurrentChange = (val: string) => {
  pageable.value.pageNumber = val
  getUserList()
}
const resetSearch = () => {
  searchParam.value = ''
  getUserList()
}
const handleAddMember = () => {
  memberRoleDialogRef.value?.toggleDialog(true)
}
const handleDelete = (userId: number | string) => {
  RoleService.role.delUserRole(roleKey.value, userId).then(() => {
    ElMessage.success('删除成功')
    getUserList()
  })
}

watch(
  roleKey,
  () => {
    getUserList()
  },
  { immediate: true }
)

onMounted(() => {
  ConfigService.config.getConfigByKey('sys_user_sex').then((data: Config) => {
    dictSex.value = JSON.parse(data.configValue)
  })
})
</script>

<template>
  <dynamic-table
    :data="pageable.records"
    :columns="tableColumns"
    :pagination="{
      currentPage: parseInt(pageable.pageNumber),
      pageSize: parseInt(pageable.pageSize),
      total: parseInt(pageable.totalRow)
    }"
    @query-list="getUserList"
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
  >
    <template #tableHeader>
      <div>
        <el-input
          v-model="searchParam"
          placeholder="姓名/手机号/登录账号"
          style="width: 220px"
        />
        <el-button
          type="primary"
          style="margin-left: var(--size-2)"
          @click="getUserList"
        >
          搜 索
        </el-button>
        <el-button
          style="margin-left: var(--size-2)"
          @click="resetSearch"
        >
          重 置
        </el-button>
      </div>
    </template>
    <template #sex="{ row }">
      {{ transform(row) }}
    </template>
    <template #toolInsert>
      <el-button
        type="primary"
        @click="handleAddMember"
      >
        添加用户
      </el-button>
    </template>
    <template #operationColumn="{ row }">
      <div>
        <el-button
          v-if="row.userId !== '1'"
          size="small"
          type="danger"
          :icon="Delete"
          text
          @click="handleDelete(row.userId)"
        >
          移除
        </el-button>
      </div>
    </template>
  </dynamic-table>
  <member-role-dialog
    ref="memberRoleDialogRef"
    :role-key="roleKey"
    @refresh="getUserList"
  />
</template>

<style scoped></style>
