<script setup lang="ts">
import { defineComponent, onMounted, ref } from 'vue'
import DynamicTable from '@components/Table/DynamicTable.vue'
import { Config, Pageable } from '@api/types/sys-api'
import ConfigInfo from '@/views/system/config/dialog/configInfo.vue'
import { ConfigService } from '@api/sys-api.ts'
import { useConfirmHandle } from '@/hooks/useConfirmHandle.ts'
import { ColumnExtProp } from '@components/Table/types/column-props.ts'
import { Delete, EditPen } from '@element-plus/icons-vue'

defineComponent({
  name: 'Config'
})

const tableColumns: ColumnExtProp[] = [
  {
    label: '参数名称',
    prop: 'configName'
  },
  {
    label: '参数键名',
    prop: 'configKey'
  },
  {
    label: '参数键值',
    prop: 'configValue',
    showOverflowTooltip: true
  },
  {
    label: '系统内置',
    prop: 'configType'
  },
  {
    label: '备注',
    prop: 'remark'
  }
]
const searchParam = ref<string>('')
const pageable = ref<Pageable<Config>>({ pageNumber: '1', pageSize: '10', totalRow: '0', records: [] })
const configFormRef = ref<InstanceType<typeof ConfigInfo> | null>()
const title = ref<string>('')

const getConfigList = () => {
  ConfigService.config.getList(searchParam.value).then((data: Pageable<Config>) => {
    pageable.value = data
  })
}
const resetSearch = () => {
  searchParam.value = ''
  getConfigList()
}
const handleAdd = () => {
  title.value = '添加参数'
  configFormRef.value?.acceptParams()
}
const handleUpdate = (row: Config) => {
  title.value = '编辑参数'
  ConfigService.config.getInfoById(row.configId).then((data: Config) => {
    configFormRef.value?.acceptParams(data)
  })
}
const handleDelete = (row: Config) => {
  useConfirmHandle(ConfigService.config.del, row.configId, `删除配置参数为${row.configName}的数据项`).then(() => {
    getConfigList()
  })
}

onMounted(() => {
  getConfigList()
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
            placeholder="参数名称/参数key"
            clearable
            style="width: 220px"
            @keyup.enter.native="getConfigList"
          />
          <el-button
            type="primary"
            style="margin-left: var(--size-2)"
            @click="getConfigList"
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
          :data="pageable.records"
          :columns="tableColumns"
          :pagination="{
            currentPage: parseInt(pageable.pageNumber),
            pageSize: parseInt(pageable.pageSize),
            total: parseInt(pageable.totalRow)
          }"
          @query-list="getConfigList"
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
          <template #configType="{ row }">
            <el-tag
              v-if="row.configType === 'Y'"
              type="primary"
            >
              是
            </el-tag>
            <el-tag
              v-else-if="row.configType === 'N'"
              type="primary"
            >
              否
            </el-tag>
          </template>
          <template #operationColumn="{ row }">
            <div>
              <el-button
                v-if="row.configType === 'N'"
                size="small"
                type="primary"
                :icon="EditPen"
                text
                @click="handleUpdate(row)"
              >
                编辑
              </el-button>
              <el-button
                v-if="row.configType === 'N'"
                size="small"
                type="danger"
                :icon="Delete"
                text
                @click="handleDelete(row)"
              >
                删除
              </el-button>
            </div>
          </template>
        </dynamic-table>
      </el-card>
    </div>
    <config-info
      ref="configFormRef"
      :title="title"
      @refresh="getConfigList"
    />
  </div>
</template>

<style scoped></style>
