<script setup lang="ts">
import { computed, defineComponent, h, ref } from 'vue'
import { useRoute } from 'vue-router'
import scale from '@/views/visit/scale.json'
import scaleAnswerData from '@/views/visit/scaleAnswers.json'

defineComponent({
  name: 'Form'
})

const route = useRoute()
const { id, key, status } = route.query
const scaleData = computed(() => {
  return scale.find((item: any) => item.hasOwnProperty(key))?.[key! as string] || {}
})
const answers = computed(() => {
  return (status === '1' && scaleAnswerData.find((item: any) => item.id == id)?.answer) || {}
})
const disabled = ref(status === '1')
const anchors = [100, Math.round(0.4 * window.innerHeight), Math.round(0.9 * window.innerHeight)]

const conclusion = computed(() => {
  if (status === '0' || !scaleData.value?.evaluationCriteria) {
    return ''
  }

  const score = scaleAnswerData.find((item: any) => item.id == id)?.score || 0
  const { rules, scores, colors } = scaleData.value.evaluationCriteria

  const evaluateRule = (rule: string, currentScore: number) => {
    try {
      const fn = new Function('score', `return ${rule.replace(/score/g, 'score')}`)
      return fn(currentScore)
    } catch (_) {
      return false
    }
  }

  const matchedRule = Object.entries(rules).find(([rule, _]) => evaluateRule(rule, score))
  if (matchedRule) {
    const [_, ruleValue] = matchedRule
    const description = scores[ruleValue! as string] || ''
    const color = colors[ruleValue! as string] || ''
    return h(
      'div',
      {
        class: `p-15px text-lg`,
        style: { color: color }
      },
      [h('p', {}, `得分：${score}`), h('p', {}, `评价：${description}`)]
    )
  }

  return ''
})
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
      <van-cell-group
        v-for="q in scaleData.questions"
        inset
      >
        <van-field
          :name="q.id"
          :label="q.questionText"
          label-align="top"
        >
          <template #input>
            <van-radio-group
              v-model="answers[q.id]"
              :disabled="disabled"
            >
              <van-space
                direction="vertical"
                fill
              >
                <van-radio
                  v-for="(value, key) in q.options"
                  :name="value"
                >
                  {{ key }}
                </van-radio>
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
      v-model:height="anchors[1]"
      :anchors="anchors"
      v-if="status === '1'"
    >
      <component :is="conclusion" />
    </van-floating-panel>
  </van-form>
</template>

<style scoped></style>
