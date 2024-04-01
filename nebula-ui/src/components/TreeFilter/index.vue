<template>
  <el-card
    class="filter card rounded is-plain"
    shadow="never"
  >
    <h4
      v-if="title"
      class="title sle"
    >
      {{ title }}
    </h4>
    <el-input
      v-model="filterText"
      placeholder="输入关键字进行过滤"
      clearable
    />
    <el-scrollbar :style="{ height: title ? `calc(100% - 95px)` : `calc(100% - 56px)` }">
      <el-tree
        ref="treeRef"
        default-expand-all
        :node-key="id"
        :data="treeData"
        :current-node-key="defaultValue"
        :highlight-current="true"
        :expand-on-click-node="false"
        :props="defaultProps"
        :filter-node-method="filterNode"
        @node-click="handleNodeClick"
      />
    </el-scrollbar>
  </el-card>
</template>

<script setup lang="ts">
import { defineComponent, onMounted, ref, watch, watchEffect } from 'vue'
import { ElTree } from 'element-plus'

defineComponent({
  name: 'TreeFilter'
})

interface Props {
  /** 分类数据，如果有分类数据，则优先于 api 请求 ==> 非必传 */
  data?: any[]
  /** 请求分类数据的 api ==> 非必传 */
  requestApi?: Promise<any>
  /** treeFilter 标题 ==> 非必传 */
  title?: string
  /** 选择的id ==> 非必传，默认为 “id” */
  id?: string
  /** 显示的label ==> 非必传，默认为 “label” */
  label?: string
  /** 默认选中的值 ==> 非必传，默认为 "" */
  defaultValue?: string
}

const props = withDefaults(defineProps<Props>(), {
  id: 'id',
  label: 'label',
  defaultValue: ''
})
const emit = defineEmits(['change'])
const defaultProps = {
  children: 'children',
  label: props.label
}
const filterText = ref<string>('')
const treeRef = ref<any>()
const treeData = ref<Array<any>>([])

const filterNode = (value: any, data: any) => {
  if (!value) return true
  return data[props.label].includes(value)
}
const handleNodeClick = (data: any) => {
  emit('change', data[props.id])
}

onMounted(async () => {
  if (!props.data && props.requestApi) {
    const data: any[] = await props.requestApi
    treeData.value = [...data]
  }
})

watchEffect(() => {
  if (props.data && props.data?.length > 0) {
    treeData.value = props.data
  }
})

watch(filterText, (val) => {
  treeRef.value?.filter(val)
})
</script>

<style scoped>
@import './index.css';
</style>
