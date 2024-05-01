<script setup lang="ts">
import { defineComponent, ref } from 'vue'
import { Consult } from '@/views/consult/index.vue'
import router from '@/router'
import dayjs from 'dayjs'

defineComponent({
  name: 'ConsultForm'
})

const form = ref<Consult>({} as Consult)
const submit = () => {
  Object.assign(form.value, { date: dayjs().format('YYYY-MM-DD') })
  router.push({
    name: 'consult',
    query: {
      ...form.value
    }
  })
}
</script>

<template>
  <van-form
    class="mt-20px"
    @submit="submit"
  >
    <van-space
      direction="vertical"
      fill
    >
      <van-cell-group inset>
        <van-field
          v-model="form.questionTitle"
          label="咨询问题"
          placeholder="请输入问题"
          :required="true"
          label-align="top"
        />
      </van-cell-group>
      <van-cell-group inset>
        <van-field
          v-model="form.desc"
          rows="10"
          autosize
          label="问题详情"
          type="textarea"
          maxlength="500"
          placeholder="请输入问题详情"
          show-word-limit
          label-align="top"
        />
      </van-cell-group>
      <div class="m-16px">
        <van-button
          round
          block
          type="primary"
          native-type="submit"
        >
          提交
        </van-button>
      </div>
    </van-space>
  </van-form>
</template>

<style scoped></style>
