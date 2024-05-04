<script setup lang="ts">
import { defineComponent, ref } from 'vue'
import router from '@/router'
import { useRoute } from 'vue-router'

defineComponent({
  name: 'consult'
})

export interface Consult {
  id: number
  questionTitle: string
  desc: string
  date: string
  status: number
  reply?: string
}

const route = useRoute()
const list = ref<Array<Consult>>([])
const loading = ref(false)
const finished = ref(false)
const refreshing = ref(false)
const json = ref([
  {
    id: 1,
    questionTitle: '如何服用？患者有药物过敏史，但忘记了具体何药过敏，担心不良反应？',
    desc: '患者 ，女 ， 67 岁， 诊断：高血压、血脂异常、心律失常、急性支气管炎\n              Rx：克拉霉素\xa0 0.25 g*12 片 /\xa0 1 片， Q12 h\n              辛伐他汀\xa0 20 mg*40 片 ，1 片 ， Qd\n\n              如何服用？患者有药物过敏史，但忘记了具体何药过敏，担心不良反应？\n             ',
    date: '2024-04-25',
    status: 1,
    reply:
      '辛伐他汀与克拉霉素不宜合用，会导致横纹肌溶解症风险增加；另外患者心律失常，考虑克拉霉素心脏毒性，也不宜使用克拉霉素，建议医生改用其他抗菌药物；建议患者改善生活方式：低脂、低油、低盐饮食；按时规律服药；并监测肝功能和肌酸激酶 ，注意有无药物过敏症状，如不适及时就诊。'
  },
  {
    id: 2,
    questionTitle: '正在使用拉坦前列素滴眼液能否同用？',
    desc: '患者，女，68 岁，诊断冠心病、心律失常\n              Rx：单硝酸异山梨酯：10 mg，Tid\n              普罗帕酮片：100 mg，Tid\n\n              正在使用拉坦前列素滴眼液能否同用？\n             ',
    status: 0,
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
    if (Object.keys(route.query).length !== 0) {
      list.value.unshift(route.query)
    }
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
  router.push('/consult/form')
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
          <van-cell :to="{ name: 'consultDetail', query: { ...item } }">
            <template #title>
              <div class="custom-title">
                <van-text-ellipsis :content="item.questionTitle" />
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
            <template #right-icon>
              <div
                class="m-l-10px"
                :class="['score', { finished: item.status === 1 }]"
              >
                {{ item.status === 1 ? '已完成' : '待回复' }}
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
