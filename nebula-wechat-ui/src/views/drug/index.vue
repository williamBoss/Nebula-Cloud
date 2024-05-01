<script setup lang="ts">
import { defineComponent, ref } from 'vue'
import dayjs from 'dayjs'
import { showToast } from 'vant'

defineComponent({
  name: 'Drug'
})

const show = ref(false)
const actions = [{ name: '跳过' }, { name: '延后30分钟' }, { name: '已服用' }]
const onSelect = (item) => {
  // 默认情况下点击选项时不会自动收起
  // 可以通过 close-on-click-action 属性开启自动收起
  show.value = false
  showToast(item.name)
}
</script>

<template>
  <div class="mt-16px">
    <van-space
      direction="vertical"
      fill
    >
      <van-cell-group inset>
        <van-cell>
          <template #title>
            <span class="text-4 text-blue">今天</span>
          </template>
        </van-cell>
        <van-cell
          title="10:00 阿司匹林肠溶片"
          is-link
          @click="show = true"
        >
          <template #value>
            <div class="text-5">
              <van-icon
                class="text-blue"
                name="checked"
              />
            </div>
          </template>
        </van-cell>
        <van-cell
          title="12:00 阿司匹林肠溶片"
          is-link
        >
          <template #value>
            <div class="text-5">
              <van-icon
                class="text-red"
                name="clock"
              />
            </div>
          </template>
        </van-cell>
        <van-cell
          title="15:00 阿司匹林肠溶片"
          is-link
        >
          <template #value>
            <div class="text-5">
              <van-icon
                class="text-orange"
                name="clock"
              />
            </div>
          </template>
        </van-cell>
      </van-cell-group>
      <van-cell-group
        v-for="i in 5"
        inset
      >
        <van-cell>
          <template #title>
            <span class="text-4 text-blue">{{ dayjs().subtract(i, 'day').format('YYYY年MM月DD日') }}</span>
          </template>
        </van-cell>
        <van-cell title="10:00 阿司匹林肠溶片">
          <template #value>
            <div class="text-5">
              <van-icon
                class="text-blue"
                name="checked"
              />
            </div>
          </template>
        </van-cell>
        <van-cell title="12:00 阿司匹林肠溶片">
          <template #value>
            <div class="text-5">
              <van-icon
                class="text-red"
                name="warning"
              />
            </div>
          </template>
        </van-cell>
        <van-cell title="15:00 阿司匹林肠溶片">
          <template #value>
            <div class="text-5">
              <van-icon
                class="text-orange"
                name="clock"
              />
            </div>
          </template>
        </van-cell>
      </van-cell-group>
    </van-space>
  </div>
  <van-action-sheet
    v-model:show="show"
    :actions="actions"
    @select="onSelect"
  />
</template>

<style scoped></style>
