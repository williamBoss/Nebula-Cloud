<script setup lang="ts">
import { fieldCommonDefaultProps, FieldCommonProps } from '@components/FormRender/FormWidget/common.js'
import { defineComponent } from 'vue'
import StaticContentWrapper from '@components/FormRender/FormWidget/StaticContentWrapper.vue'

defineComponent({
  name: 'DividerWidget'
})

withDefaults(defineProps<FieldCommonProps>(), fieldCommonDefaultProps)
</script>

<template>
  <static-content-wrapper :field="field">
    <el-divider
      ref="fieldEditor"
      direction="horizontal"
      :content-position="field.options.contentPosition"
    >
      {{ field.options.label }}
    </el-divider>
  </static-content-wrapper>
</template>

<style scoped></style>
