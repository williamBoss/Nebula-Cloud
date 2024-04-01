<script setup lang="ts">
import { computed, defineComponent, onMounted, onUnmounted, ref, shallowRef } from 'vue'
import { ECharts, EChartsOption } from 'echarts'
import * as echarts from 'echarts'
import _ from 'lodash'

defineComponent({
  name: 'BasePie'
})

interface Props {
  width?: string
  height?: string
  option: EChartsOption
}

const props = withDefaults(defineProps<Props>(), {
  width: '100%',
  height: '300px',
  option: () => ({})
})
const pieChartContainer = ref<HTMLElement | null>()
const pieChartInstance = shallowRef<ECharts | null>()
const defaultOptions = ref({
  legend: {
    top: 'bottom'
  },
  toolbox: {
    feature: {
      saveAsImage: { show: true }
    }
  },
  series: [
    {
      type: 'pie',
      radius: '50%'
    }
  ]
})
const mergedOption = computed<EChartsOption>(() => {
  return _.merge(defaultOptions.value, props.option)
})

const echartsInit = () => {
  pieChartInstance.value = echarts.init(pieChartContainer.value)
  pieChartInstance.value?.setOption(mergedOption.value)
}

onMounted(() => {
  echartsInit()
})

onUnmounted(() => {
  pieChartInstance.value?.dispose()
})
</script>

<template>
  <div
    ref="pieChartContainer"
    :style="{ height: height, width: width }"
  />
</template>

<style scoped></style>
