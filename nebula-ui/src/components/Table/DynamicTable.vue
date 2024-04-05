<script setup lang="ts">
import { computed, defineComponent, onMounted } from 'vue'
import { Emits, TableExtProps } from '@components/Table/types/table-props.ts'
import { Delete, EditPen, Refresh } from '@element-plus/icons-vue'
import _ from 'lodash'

defineComponent({
  name: 'DynamicTable'
})

const props = withDefaults(defineProps<TableExtProps>(), {
  height: '100%',
  fit: true,
  showHeader: true,
  headerRowClassName: 'table-header-row-class',
  headerCellClassName: 'table-header-cell-class',
  selectOnIndeterminate: true,
  loading: false,
  loadingConfig: () => ({
    text: '加载中......'
  }),
  hiddenOperation: false,
  hiddenPagination: false,
  pagination: () => ({
    currentPage: 1,
    pageSize: 10,
    total: 0,
    pageSizes: [10, 25, 50, 100],
    layout: 'total, sizes, prev, pager, next, jumper',
    background: true
  }),
  initParam: {}
})
const emits = defineEmits<Emits>()
const getProps = computed(() => {
  return Object.entries(props).reduce<Partial<TableExtProps>>((resetProps, [key, value]) => {
    value !== undefined && ((resetProps as any)[key] = value)
    return resetProps
  }, {})
})
const pagination = computed(() => {
  return _.merge(
    {
      pageSizes: [10, 25, 50, 100],
      layout: 'total, sizes, prev, pager, next, jumper',
      background: true
    },
    props.pagination
  )
})

const handleQuery = () => {
  emits('query-list', props.initParam)
}
const handleSizeChange = (size: string) => {
  emits('size-change', size)
}
const handleCurrentChange = (currentPage: string) => {
  emits('current-change', currentPage)
}
const handleUpdate = (row: any) => {
  emits('update', row)
}
const handleDelete = (row: any) => {
  emits('delete', row)
}

onMounted(() => {})
</script>

<template>
  <!-- 表格 -->
  <div class="table">
    <!-- 表格头部 操作按钮 -->
    <div class="table-header">
      <div class="flx-justify-between table-operation">
        <div>
          <slot name="tableHeader" />
        </div>
        <div>
          <slot name="tableTools">
            <slot name="toolInsert" />
            <el-button
              :icon="Refresh"
              circle
              @click="handleQuery"
            />
            <slot name="toolAppend" />
          </slot>
        </div>
      </div>
    </div>

    <!-- 表格主体 -->
    <el-table
      ref="tableRef"
      :="getProps"
      v-loading="loading"
      :element-loading-text="loadingConfig.text"
      :element-loading-spinner="loadingConfig.spinner"
      :element-loading-svg="loadingConfig.svg"
      :element-loading-background="loadingConfig.background"
    >
      <slot />
      <el-table-column
        v-for="(item, index) in columns"
        :="item"
        :key="index"
      >
        <template
          v-if="$slots[item.prop]"
          #default="scope"
        >
          <slot
            :name="item.prop"
            v-bind="scope"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        width="160"
        v-if="!hiddenOperation"
      >
        <template #default="scope">
          <slot
            name="operationColumn"
            v-bind="scope"
          >
            <el-button
              size="small"
              type="primary"
              :icon="EditPen"
              text
              @click="handleUpdate(scope.row)"
            >
              编辑
            </el-button>
            <el-button
              size="small"
              type="danger"
              :icon="Delete"
              text
              @click="handleDelete(scope.row)"
            >
              删除
            </el-button>
          </slot>
        </template>
      </el-table-column>
      <!-- 插入表格最后一行之后的插槽 -->
      <template #append>
        <slot name="append" />
      </template>
      <!-- 无数据 -->
      <template #empty>
        <slot name="empty" />
      </template>
    </el-table>

    <!-- 分页组件 -->
    <slot name="pagination">
      <div
        v-if="!hiddenPagination"
        class="flx flx-right"
        style="padding-top: var(--size-3)"
      >
        <el-pagination
          :="pagination"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </slot>
  </div>
</template>

<style scoped></style>
