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
  name: 'DateRangeWidget'
})

const props = withDefaults(defineProps<FieldCommonProps>(), fieldCommonDefaultProps)
const fieldEditor = ref()
const oldFieldValue = ref(null)
const fieldModel = ref(null)
const rules = ref([])

const { widgetSize, customClass } = useCommonComputed(props)
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
    <el-date-picker
      ref="fieldEditor"
      v-model="fieldModel"
      :class="[customClass, field.options.autoFullWidth ? 'auto-full-width' : '']"
      :type="field.options.type"
      :disabled="field.options.disabled"
      :readonly="field.options.readonly"
      :size="widgetSize"
      :clearable="field.options.clearable"
      :editable="field.options.editable"
      :format="field.options.format"
      :value-format="field.options.valueFormat"
      :start-placeholder="field.options.startPlaceholder"
      :end-placeholder="field.options.endPlaceholder"
      @focus="handleFocusCustomEvent"
      @blur="handleBlurCustomEvent"
      @change="handleChangeEvent"
    >
    </el-date-picker>
  </form-item-wrapper>
</template>

<style scoped>
:deep(.auto-full-width.el-date-editor.el-input),
:deep(.auto-full-width.el-date-editor .el-input__wrapper) {
  width: 100% !important;
}
</style>
