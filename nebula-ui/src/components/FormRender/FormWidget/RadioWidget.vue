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
  name: 'RadioWidget'
})

const props = withDefaults(defineProps<FieldCommonProps>(), fieldCommonDefaultProps)
const instance = getCurrentInstance()
const fieldEditor = ref()
const oldFieldValue = ref(null)
const fieldModel = ref(null)
const rules = ref([])

const { widgetSize, customClass } = useCommonComputed(props)
const { handleChangeEvent } = useEventFunction(instance, props, oldFieldValue)
const { initFieldModel } = useInitField(props, oldFieldValue, fieldModel)
initFieldModel?.()

const radioChange = (e) => {
  e === fieldModel.value ? (fieldModel.value = '') : (fieldModel.value = e)
}
</script>

<template>
  <form-item-wrapper
    :field="field"
    :rules="rules"
  >
    <el-radio-group
      ref="fieldEditor"
      v-model="fieldModel"
      :class="[customClass]"
      :disabled="field.options.disabled"
      :size="widgetSize"
      @change="handleChangeEvent"
    >
      <template v-if="field.options.buttonStyle">
        <el-radio-button
          v-for="(item, index) in field.options.optionItems"
          :key="index"
          :label="item.value"
          :disabled="item.disabled"
          @click.prevent="radioChange(item.value)"
        >
          {{ item.label }}
        </el-radio-button>
      </template>
      <template v-else>
        <div class="radio-container">
          <el-radio
            v-for="(item, index) in field.options.optionItems"
            :key="index"
            :class="{
              'el-radio': true,
              'row-radio-option': field.options.isRowRadioOption
            }"
            :label="item.value"
            :disabled="item.disabled"
            @click.prevent="radioChange(item.value)"
          >
            {{ item.label }}
          </el-radio>
        </div>
      </template>
    </el-radio-group>
  </form-item-wrapper>
</template>

<style scoped>
:deep(.el-radio-button) {
  margin-right: 15px;
}

:deep(.el-radio-button) .el-radio-button__inner {
  background: #eaeaf9;
  color: #4949c9;
  border-radius: 4px;
  border: 0;
}

::v-deep(.el-radio-button).is-active .el-radio-button__inner {
  background: #4949c9;
  color: #ffffff;
}

.radio-container {
  display: flex;
  flex-wrap: wrap;
}

.radio-container .el-radio {
  margin-bottom: 10px;
}

.row-radio-option {
  flex: 0 0 100%;
}
</style>
