<script setup lang="ts">
import { defineComponent, ref } from 'vue'
import { useRoute } from 'vue-router'

defineComponent({
  name: 'Form'
})

const route = useRoute()
const { id, status } = route.query
const answers = ref({})
const disabled = ref(status === '1')
const anchors = [100, Math.round(0.4 * window.innerHeight), Math.round(0.9 * window.innerHeight)]
</script>

<template>
  <van-form
    class="mt-20px"
    :disabled="disabled"
  >
    <van-space
      direction="vertical"
      fill
      :size="20"
    >
      <van-cell-group inset>
        <van-field
          name="st"
          label="单选框1"
          label-align="top"
        >
          <template #input>
            <van-radio-group
              v-model="answers.st"
              :disabled="disabled"
            >
              <van-space
                direction="vertical"
                fill
              >
                <van-radio name="1">单选框 1</van-radio>
                <van-radio name="2">单选框 2</van-radio>
              </van-space>
            </van-radio-group>
          </template>
        </van-field>
      </van-cell-group>
    </van-space>
    <div
      class="m-16px"
      v-if="!disabled"
    >
      <van-button
        round
        block
        type="primary"
        native-type="submit"
      >
        提交
      </van-button>
    </div>
    <van-floating-panel
      v-model:height="anchors[2]"
      :anchors="anchors"
    >
      <div style="text-align: center; padding: 15px">
        <p>
          1-7题的备选答案为“是”、“否”，答“是”记0分，“否”记1分，其中第5题反向计分，第8题答案，选择从上往下的答案，分别记1分，0.75分，0.5分，0.25分，0分。量表满分8分，得分
          < 6分为依从性差，得分6-8分为依从性中等，得分8分为依从性好。
        </p>
      </div>
    </van-floating-panel>
  </van-form>
</template>

<style scoped></style>
