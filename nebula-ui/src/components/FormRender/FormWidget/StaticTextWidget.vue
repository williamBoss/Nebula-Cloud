<script setup lang="ts">
import { defineComponent } from 'vue'
import StaticContentWrapper from '@components/FormRender/FormWidget/StaticContentWrapper.vue'
import { fieldCommonDefaultProps, FieldCommonProps } from '@components/FormRender/FormWidget/common.ts'

defineComponent({
  name: 'StaticTextWidget'
})

withDefaults(defineProps<FieldCommonProps>(), fieldCommonDefaultProps)
</script>

<template>
  <static-content-wrapper :field="field">
    <div>
      <pre class="static-text">{{ field.options.textContent }}</pre>
    </div>
  </static-content-wrapper>
</template>

<style scoped>
.static-text {
  font-size: 14px;
  font-family: inherit;
  font-weight: 500;
  color: #222222;
  line-height: 16px;
}
</style>
