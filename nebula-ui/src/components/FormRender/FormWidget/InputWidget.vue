<script setup lang="ts">
import FormItemWrapper from '@components/FormRender/FormWidget/FormItemWrapper.vue'
import { computed, defineComponent, getCurrentInstance, ref } from 'vue'
import {
  fieldCommonDefaultProps,
  FieldCommonProps,
  useCommonComputed,
  useEventFunction,
  useInitField
} from '@components/FormRender/FormWidget/common.js'

defineComponent({
  name: 'InputWidget'
})

const props = withDefaults(defineProps<FieldCommonProps>(), fieldCommonDefaultProps)
const fieldEditor = ref()
const oldFieldValue = ref(null)
const fieldModel = ref(null)
const rules = ref([])
const inputType = computed(() =>
  props.field.options.type && props.field.options.type !== 'number' ? props.field.options.type : 'text'
)

const { widgetSize } = useCommonComputed(props)
const { handleBlurCustomEvent, handleFocusCustomEvent, handleChangeEvent, handleInputCustomEvent } = useEventFunction(
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
    <el-input
      ref="fieldEditor"
      v-model="fieldModel"
      :type="inputType"
      :maxlength="field.options.maxLength"
      :minlength="field.options.minLength"
      :show-word-limit="field.options.showWordLimit"
      :placeholder="field.options.placeholder"
      :clearable="field.options.clearable"
      :show-password="field.options.showPassword"
      :disabled="field.options.disabled"
      :size="widgetSize"
      :prefix-icon="field.options.prefixIcon"
      :suffix-icon="field.options.suffixIcon"
      :autosize="field.options.autosize"
      :readonly="field.options.readonly"
      @blur="handleBlurCustomEvent"
      @focus="handleFocusCustomEvent"
      @change="handleChangeEvent"
      @input="handleInputCustomEvent"
    />
  </form-item-wrapper>
</template>

<style scoped></style>
