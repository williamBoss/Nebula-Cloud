<script setup lang="ts">
import { defineComponent, onMounted, ref } from 'vue'
import DynamicTable from '@components/Table/DynamicTable.vue'
import { ColumnExtProp } from '@components/Table/types/column-props.ts'
import { Menu, Pageable } from '@api/types/sys-api'
import { MenuService } from '@api/sys-api.ts'
import * as Icons from '@element-plus/icons-vue'
import { useConfirmHandle } from '@/hooks/useConfirmHandle.ts'
import MenuInfo from '@/views/system/menu/dialog/menuInfo.vue'

defineComponent({
  name: 'Menu'
})

const tableColumns = ref<Array<ColumnExtProp>>([
  {
    prop: 'menuName',
    label: '菜单名称'
  },
  {
    prop: 'menuType',
    label: '类型'
  },
  {
    prop: 'icon',
    label: '图标'
  },
  {
    prop: 'path',
    label: '路径'
  },
  {
    prop: 'component',
    label: '组件'
  },
  {
    prop: 'perms',
    label: '权限字符'
  },
  {
    prop: 'orderNum',
    label: '显示顺序'
  }
])
const pageable = ref<Pageable<Menu>>({ pageNumber: '1', pageSize: '10', totalRow: '0', records: [] })
const searchParam = ref<string>('')
const menuFormRef = ref<InstanceType<typeof MenuInfo> | null>()
const title = ref<string>('')

const getMenuList = () => {
  const { records, ...pageParam } = pageable.value
  MenuService.menu
    .getList(Object.assign({}, { searchKey: searchParam.value }, pageParam))
    .then((data: Pageable<Menu>) => {
      pageable.value = data
    })
}
const handleSizeChange = (val: string) => {
  pageable.value.pageNumber = '1'
  pageable.value.pageSize = val
  getMenuList()
}
const handleCurrentChange = (val: string) => {
  pageable.value.pageNumber = val
  getMenuList()
}
const resetSearch = () => {
  searchParam.value = ''
  getMenuList()
}
const resolveIcon = (iconName: string) => {
  return Icons[iconName as keyof typeof Icons] || null
}
const handleAdd = () => {
  title.value = '添加菜单'
  menuFormRef.value?.acceptParams()
}
const handleUpdate = (row: Menu) => {
  title.value = '修改菜单'
  MenuService.menu.getInfoById(row.menuId).then((data: Menu) => {
    menuFormRef.value?.acceptParams(data)
  })
}
const handleDelete = (row: Menu) => {
  useConfirmHandle(MenuService.menu.del, row.menuId, `删除菜单-${row.menuName}的数据项`).then(() => {
    getMenuList()
  })
}

onMounted(() => {
  getMenuList()
})
</script>

<template>
  <div class="table">
    <el-card
      class="card rounded is-plain"
      shadow="never"
    >
      <dynamic-table
        :columns="tableColumns"
        :data="pageable.records"
        :pagination="{
          currentPage: parseInt(pageable.pageNumber),
          pageSize: parseInt(pageable.pageSize),
          total: parseInt(pageable.totalRow)
        }"
        @query-list="getMenuList"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        @update="handleUpdate"
        @delete="handleDelete"
      >
        <template #tableHeader>
          <div>
            <el-input
              v-model="searchParam"
              placeholder="菜单名称/路由地址"
              clearable
              style="width: 220px"
              @keyup.enter.native="getMenuList"
            />
            <el-button
              type="primary"
              style="margin-left: var(--size-2)"
              @click="getMenuList"
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
        <template #toolInsert>
          <el-button
            type="primary"
            @click="handleAdd"
          >
            添加菜单
          </el-button>
        </template>
        <template #menuType="{ row }">
          <el-tag
            v-if="row.menuType === 'M'"
            type="info"
          >
            目录
          </el-tag>
          <el-tag
            v-else-if="row.menuType === 'C'"
            type="warning"
          >
            菜单
          </el-tag>
          <el-tag
            v-else-if="row.menuType === 'F'"
            type="success"
          >
            按钮
          </el-tag>
        </template>
        <template #icon="{ row }">
          <el-icon>
            <svg-icon
              v-if="row.iconType === 'sl'"
              :name="row.icon"
            />
            <component
              v-else-if="row.iconType === 'el'"
              :is="resolveIcon(row.icon)"
            />
          </el-icon>
        </template>
      </dynamic-table>
    </el-card>
    <menu-info
      ref="menuFormRef"
      :title="title"
      @refresh="getMenuList"
    />
  </div>
</template>

<style scoped></style>
