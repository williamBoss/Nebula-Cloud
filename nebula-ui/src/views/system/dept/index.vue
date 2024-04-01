<script setup lang="ts">
import { defineComponent, onMounted, ref } from 'vue'
import DynamicTable from '@components/Table/DynamicTable.vue'
import { Dept } from '@api/types/sys-api'
import { DeptService } from '@api/sys-api.ts'
import { useConfirmHandle } from '@/hooks/useConfirmHandle.ts'
import { ColumnExtProp } from '@components/Table/types/column-props.ts'
import DeptInfo from '@/views/system/dept/dialog/deptInfo.vue'

defineComponent({
  name: 'Dept'
})

const tableColumns: ColumnExtProp[] = [
  { prop: 'deptName', label: '部门名称' },
  { prop: 'leaderName', label: '负责人' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'email', label: '邮箱' },
  { prop: 'status', label: '状态' }
]
const dataList = ref<Dept[]>([])
const searchParam = ref<string>('')
const deptFormRef = ref<InstanceType<typeof DeptInfo> | null>()
const title = ref<string>('')

const getDeptList = () => {
  DeptService.dept.getList(searchParam.value).then((data: Dept[]) => {
    dataList.value = data
  })
}
const resetSearch = () => {
  searchParam.value = ''
  getDeptList()
}
const handleAdd = () => {
  title.value = '添加部门'
  deptFormRef.value?.acceptParams()
}
const handleUpdate = (row: Dept) => {
  title.value = '编辑部门'
  DeptService.dept.getInfoById(row.deptId).then((data: Dept) => {
    deptFormRef.value?.acceptParams(data)
  })
}
const handleDelete = (row: Dept) => {
  useConfirmHandle(DeptService.dept.del, row.deptId, `删除部门-${row.deptName}的数据项`).then(() => {
    getDeptList()
  })
}

onMounted(() => {
  getDeptList()
})
</script>

<template>
  <div>
    <div class="table">
      <el-card
        class="table-search card is-plain rounded"
        shadow="never"
      >
        <div>
          <el-input
            v-model="searchParam"
            placeholder="部门名称/负责人"
            clearable
            style="width: 220px"
            @keyup.enter.native="getDeptList"
          />
          <el-button
            type="primary"
            style="margin-left: var(--size-2)"
            @click="getDeptList"
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
      </el-card>
      <el-card
        class="card is-plain rounded"
        shadow="never"
      >
        <dynamic-table
          :data="dataList"
          :columns="tableColumns"
          :hidden-pagination="true"
          row-key="deptId"
          default-expand-all
          @query-list="getDeptList"
          @update="handleUpdate"
          @delete="handleDelete"
        >
          <template #tableHeader>
            <div class="flex">
              <el-button
                type="primary"
                @click="handleAdd"
              >
                新增
              </el-button>
            </div>
          </template>
          <template #status="{ row }">
            <el-tag
              v-if="row.status === '0'"
              type="primary"
            >
              启用
            </el-tag>
            <el-tag
              v-else
              type="danger"
            >
              禁用
            </el-tag>
          </template>
        </dynamic-table>
      </el-card>
    </div>
    <dept-info
      ref="deptFormRef"
      :title="title"
      :dept-list="dataList"
      @refresh="getDeptList"
    />
  </div>
</template>

<style scoped></style>
