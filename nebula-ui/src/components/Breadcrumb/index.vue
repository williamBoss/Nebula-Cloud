<template>
  <el-breadcrumb separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item
        v-for="item in breadcrumbs"
        :key="item.path"
      >
        {{ item.title }}
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script setup lang="ts">
import { defineComponent, onMounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'

defineComponent({
  name: 'Breadcrumb'
})

const route = useRoute()
const breadcrumbs = ref<Array<{ path: string; title: string }>>([])

const getBreadcrumb = (): void => {
  // 当前路由信息
  const matchedRoutes = route.matched.filter((record) => record.meta.title)
  breadcrumbs.value = matchedRoutes.map((record) => ({
    title: record.meta.title as string,
    path: record.path
  }))
}

onMounted(() => {
  getBreadcrumb()
})

watch(
  () => route.path,
  () => {
    getBreadcrumb()
  },
  {
    deep: true
  }
)
</script>
