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
          >
            打卡
          </van-button>
        </van-step>
        <van-step>
          <h3>药品名称</h3>
          <p>2016-07-12 12:40</p>
        </van-step>
      </van-steps>
    </van-cell>
  </van-cell-group>
</template>

<script setup lang="ts">
import { defineComponent } from 'vue'
import punchClock from '@/assets/punchClock.svg'
import refresh from '@/assets/refresh.svg'

defineComponent({
  name: 'punchClock'
})
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
