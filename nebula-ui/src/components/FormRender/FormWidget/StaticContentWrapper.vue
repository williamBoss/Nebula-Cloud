<script setup lang="ts">
import { defineComponent } from 'vue'
import {
  fieldCommonDefaultProps,
  FieldCommonProps,
  useCommonComputed
} from '@components/FormRender/FormWidget/common.js'

defineComponent({
  name: 'StaticContentWrapper'
})

const props = withDefaults(defineProps<FieldCommonProps>(), fieldCommonDefaultProps)

const { customClass } = useCommonComputed(props)
</script>

<template>
  <div
    class="field-wrapper"
    :class="[customClass]"
  >
    <div
      v-if="!field.options.hidden"
      class="static-content-item"
    >
      <slot></slot>
    </div>
  </div>
</template>

<style scoped></style>
