<script setup lang="ts">
import { defineComponent, getCurrentInstance, ref } from 'vue'
import {
  fieldCommonDefaultProps,
  FieldCommonProps,
  useCommonComputed,
  useEventFunction,
  useInitField
} from '@components/FormRender/FormWidget/common.js'
import FormItemWrapper from '@components/FormRender/FormWidget/FormItemWrapper.vue'

defineComponent({
  name: 'SelectWidget'
})

const props = withDefaults(defineProps<FieldCommonProps>(), fieldCommonDefaultProps)
const fieldEditor = ref()
const oldFieldValue = ref(null)
const fieldModel = ref(null)
const rules = ref([])

const { widgetSize } = useCommonComputed(props)
const { handleBlurCustomEvent, handleFocusCustomEvent, handleChangeEvent } = useEventFunction(
  getCurrentInstance,
  props,
  oldFieldValue
)
const { initFieldModel } = useInitField(props, oldFieldValue, fieldModel)
initFieldModel?.()
</script>

<template>
  <form-item-wrapper
    :field="field"
    :rules="rules"
  >
    <el-select
      ref="fieldEditor"
      v-model="fieldModel"
      class="full-width-input"
      :disabled="field.options.disabled"
      :size="widgetSize"
      :clearable="field.options.clearable"
      :filterable="field.options.filterable"
      :multiple="field.options.multiple"
      :multiple-limit="field.options.multipleLimit"
      :placeholder="field.options.placeholder"
      @focus="handleFocusCustomEvent"
      @blur="handleBlurCustomEvent"
      @change="handleChangeEvent"
    >
      <el-option
        v-for="item in field.options.optionItems"
        :key="item.value"
        :label="item.label"
        :value="item.value"
        :disabled="item.disabled"
      >
      </el-option>
    </el-select>
  </form-item-wrapper>
</template>

<style scoped>
.full-width-input {
  width: 100% !important;
}
</style>
