<template>
  <el-config-provider :locale="i18nLocale">
    <router-view />
  </el-config-provider>
</template>

<script setup lang="ts">
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import en from 'element-plus/es/locale/lang/en'
import { computed } from 'vue'
import { globalStore } from '@/store/index.ts'

const global = globalStore()

// element 语言配置
const i18nLocale = computed(() => {
  if (global.language && global.language === 'zh') return zhCn
  if (global.language === 'en') return en
  return zhCn
})
</script>
