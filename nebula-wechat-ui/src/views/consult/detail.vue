<script setup lang="ts">
import { Consult } from '@/views/consult/index.vue'

const props = defineProps<Consult>()
</script>

<template>
  <van-space
    class="mt-20px"
    direction="vertical"
    fill
  >
    <van-cell-group inset>
      <van-cell
        title="咨询问题"
        :label="props.questionTitle"
      />
    </van-cell-group>
    <van-cell-group inset>
      <van-cell
        title="咨询问题描述"
        :label="props.desc"
      />
    </van-cell-group>
    <van-cell-group inset>
      <van-cell
        title="回复"
        :label="props.reply"
      />
    </van-cell-group>
  </van-space>
</template>

<style scoped></style>
