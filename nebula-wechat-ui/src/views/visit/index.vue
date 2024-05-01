<template>
  <div class="m-t-20px">
    <van-pull-refresh
      v-model="refreshing"
      @refresh="onRefresh"
    >
      <van-list
        v-model:loading="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onLoad"
      >
        <van-cell-group
          v-for="item in list"
          :key="item.id"
          class="cell-bottom h-95px"
          inset
        >
          <van-cell :to="`/visit/form?id=${item.id}&status=${item.status}`">
            <template #title>
              <div class="flex flx-justify-between">
                <div class="custom-title">{{ item.title }}</div>
                <div :class="['score', { finished: item.status === 1 }]">
                  {{ item.score || '?' }} | {{ item.totalScore }}
                </div>
              </div>
            </template>
            <template #label>
              <div class="flex flx-justify-between mt-10px">
                <div class="desc">
                  <van-icon name="notes-o" />
                  {{ item.date }}
                </div>
                <div :class="['visit-status', { finished: item.status === 1 }]">
                  {{ item.status === 1 ? '已完成' : '待填写' }}
                </div>
              </div>
            </template>
          </van-cell>
        </van-cell-group>
      </van-list>
    </van-pull-refresh>
  </div>
</template>

<script setup lang="ts">
import { defineComponent, ref } from 'vue'

defineComponent({
  name: 'Visit'
})

interface Visit {
  id: number
  title: string
  status: number
  score: number
  totalScore: number
  date: string
}

const list = ref<Array<Visit>>([])
const loading = ref(false)
const finished = ref(false)
const refreshing = ref(false)

const onLoad = () => {
  setTimeout(() => {
    if (refreshing.value) {
      list.value = []
      refreshing.value = false
    }

    list.value.push({ id: 1, title: '用药依从性', status: 0, score: 0, totalScore: 10, date: '2024-04-25' })
    list.value.push({ id: 2, title: 'SAS', status: 1, score: 15, totalScore: 20, date: '2024-04-20' })
    list.value.push({ id: 3, title: 'SDS', status: 0, score: 0, totalScore: 20, date: '2024-04-19' })
    loading.value = false
    finished.value = true
  }, 1000)
}

const onRefresh = () => {
  // 清空列表数据
  finished.value = false

  // 重新加载数据
  // 将 loading 设置为 true，表示处于加载状态
  loading.value = true
  onLoad()
}
</script>

<style scoped>
.cell-bottom {
  margin-bottom: 20px;
}

.custom-title {
  font-size: 18px;
  font-weight: 700;
  line-height: 20px;
  color: #003f38;
}

.desc {
  color: #003f38ba;
  font-size: 14px;
}

.visit-status {
  min-width: 60px;
  border-radius: 20px;
  background: #4f98ca;
  text-align: center;
  color: #fff;
}

.score {
  width: 60px;
  height: 40px;
  border-radius: 8px;
  background: #4f98ca;
  color: #ffffff;
  text-align: center;
  line-height: 40px;
}

.finished {
  background: #008000;
}
</style>
