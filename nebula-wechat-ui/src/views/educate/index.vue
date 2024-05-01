<script setup lang="ts">
import { defineComponent, ref } from 'vue'
import router from '@/router'

defineComponent({
  name: 'Educate'
})

export interface Educate {
  id: number
  title: string
  desc?: string
  date: string
}

const list = ref<Array<Educate>>([])
const loading = ref(false)
const finished = ref(false)
const refreshing = ref(false)
const json = ref([
  {
    id: 1,
    title: '全面提升健康教育：构建健康中国',
    date: '2024-04-25'
  },
  {
    id: 2,
    title: '健康教育：从知识到实践的转变',
    date: '2024-04-20'
  }
])

const onLoad = () => {
  setTimeout(() => {
    if (refreshing.value) {
      list.value = []
      refreshing.value = false
    }

    list.value.push(...json.value)
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

const onClick = () => {
  router.push('/educate/detail')
}
</script>

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
          <van-cell :to="{ name: 'educateDetail', query: { ...item } }">
            <template #title>
              <div class="custom-title">
                <van-text-ellipsis :content="item.title" />
              </div>
            </template>
            <template #label>
              <div class="flex flx-justify-between mt-10px">
                <div class="desc">
                  <van-icon name="notes-o" />
                  {{ item.date }}
                </div>
              </div>
            </template>
          </van-cell>
        </van-cell-group>
      </van-list>
    </van-pull-refresh>
  </div>
  <van-floating-bubble
    icon="chat"
    @click="onClick"
  />
</template>

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
</style>
