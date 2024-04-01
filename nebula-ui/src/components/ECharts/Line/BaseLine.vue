<script setup lang="ts">
import { computed, defineComponent, onMounted, onUnmounted, ref, shallowRef } from 'vue'
import { ECharts, EChartsOption } from 'echarts'
import * as echarts from 'echarts'
import _ from 'lodash'

defineComponent({
  name: 'BaseLine'
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
const lineChartContainer = ref<HTMLElement | null>()
const lineChartInstance = shallowRef<ECharts | null>()
const defaultOptions = ref({
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  tooltip: {
    trigger: 'axis'
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false
  },
  yAxis: {
    type: 'value'
  }
})
const mergedOption = computed<EChartsOption>(() => {
  return _.merge(defaultOptions.value, props.option)
})

const echartsInit = () => {
  lineChartInstance.value = echarts.init(lineChartContainer.value)
  lineChartInstance.value?.setOption(mergedOption.value)
}

onMounted(() => {
  echartsInit()
})

onUnmounted(() => {
  lineChartInstance.value?.dispose()
})
</script>

<template>
  <div
    ref="lineChartContainer"
    :style="{ height: height, width: width }"
  />
</template>

<style scoped></style>
