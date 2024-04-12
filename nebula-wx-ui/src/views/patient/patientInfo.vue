<template>
  <van-cell-group
    inset
    class="cell-top"
  >
    <van-cell
      title="患者姓名"
      value="xxxxx"
    />
    <van-cell
      title="性别"
      value="未知"
    />
    <van-cell
      title="生日"
      value="xxxx.xx.xx"
    />
    <van-cell
      title="年龄"
      value="19"
    />
  </van-cell-group>
  <van-cell-group
    inset
    class="cell-top"
  >
    <van-cell
      title="手机号"
      value="130xxxxxx"
    />
    <van-cell
      title="身高"
      value="xxx"
    />
    <van-cell
      title="体重"
      value="xx"
    />
    <van-cell
      title="BMI"
      value="xxx"
    />
  </van-cell-group>
</template>

<script setup lang="ts">
import { defineComponent } from 'vue'
import { globalStore } from '@/store'

defineComponent({
  name: 'PatientInfo'
})

const global = globalStore()
</script>

<style scoped>
.cell-top {
  margin: 20px;
}
</style>
