<template>
  <van-sticky>
    <van-row
      class="top"
      :gutter="20"
    >
      <van-col :span="14">
        <div class="title">
          <h3>打卡记录</h3>
        </div>
      </van-col>
      <van-col :span="10">
        <van-image
          fit="cover"
          position="left"
          :src="punchClock"
          style="width: 100px; height: 100px"
        />
      </van-col>
    </van-row>
  </van-sticky>
  <van-row>
    <van-cell-group>
      <van-calendar
        :show-title="false"
        :poppable="false"
        :show-confirm="false"
        :min-date="new Date(2022, 0, 1)"
        :max-date="new Date(2030, 11, 31)"
        :style="{ height: '350px' }"
      />
    </van-cell-group>
  </van-row>

  <van-cell-group style="margin-top: 20px">
    <van-cell title="打卡记录">
      <template #right-icon>
        <img
          :src="refresh"
          style="height: 20px"
          alt=""
        />
      </template>
    </van-cell>
    <van-cell>
      <van-steps
        direction="vertical"
        :active="-1"
      >
        <van-step>
          <van-button
            plain
            type="primary"
            size="small"
            @click="onClock"
          >
            打卡
          </van-button>
        </van-step>
        <van-step v-for="clock in clocks">
          <p class="color-black">{{ clock.drugName }}</p>
          <p>{{ clock.duration }}</p>
        </van-step>
      </van-steps>
    </van-cell>
  </van-cell-group>

  <van-action-sheet
    v-model:show="show"
    title="打卡"
    :close-on-click-overlay="false"
  >
    <div class="flx pl-16px pr-16px min-h-200px">
      <van-form
        class="flx w-full"
        style="flex-flow: row wrap"
        @submit="onSubmit"
      >
        <van-cell-group inset>
          <van-field
            v-model="drugName"
            name="drugName"
            label="药品名称"
            placeholder="请填写药品名称"
            :rules="[{ required: true, message: '请填写药品名称' }]"
          />
        </van-cell-group>
        <div
          class="flx w-full"
          style="align-self: flex-end"
        >
          <van-button
            round
            block
            type="danger"
            @click="show = false"
          >
            取消
          </van-button>
          <span class="ml-4"></span>
          <van-button
            round
            block
            type="primary"
            native-type="submit"
          >
            提交
          </van-button>
        </div>
      </van-form>
    </div>
  </van-action-sheet>
</template>

<script setup lang="ts">
import { defineComponent, ref } from 'vue'
import punchClock from '@/assets/punchClock.svg'
import refresh from '@/assets/refresh.svg'
import dayjs from 'dayjs'

defineComponent({
  name: 'punchClock'
})

const show = ref<boolean>(false)
const drugName = ref<string>('')
const clocks = ref<any[]>([{ drugName: '阿莫西林', duration: dayjs().format('YYYY-MM-DD hh:mm') }])

const onClock = () => {
  show.value = true
}
const onSubmit = (value: any) => {
  clocks.value.push({ drugName: value.drugName, duration: dayjs().format('YYYY-MM-DD hh:mm') })
  show.value = false
}
</script>

<style scoped>
.top {
  width: 100%;
  height: 100px;
  overflow: hidden;
  background: #40e0d0;
  position: relative;
}

.title {
  margin-left: 55px;
  margin-top: 35px;
  color: #fff;
}
</style>
