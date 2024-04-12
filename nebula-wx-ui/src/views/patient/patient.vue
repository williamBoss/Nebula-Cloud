<template>
  <van-row style="padding: 16px; margin-top: 20px">
    <van-col
      :span="8"
      style="text-align: center"
    >
      <van-image
        round
        width="70px"
        height="70px"
        :src="global.headImgUrl ? global.headImgUrl : 'https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg'"
      />
    </van-col>
    <van-col :span="16">
      <van-row style="height: 70px; align-items: center">
        <div class="patient_name_color">{{ global.nickName }}</div>
        <div class="hospital_name_color">医院名称</div>
      </van-row>
    </van-col>
  </van-row>
  <van-cell-group inset>
    <van-cell
      title="手机号"
      value="130xxxxxxxxx"
    />
    <van-cell
      title="BMI"
      value="31.2"
    />
    <van-cell
      title="诊断结果"
      value="xxxxx"
    />
  </van-cell-group>
  <van-row style="margin: 20px 10px">
    <van-grid
      :gutter="12"
      :column-num="3"
    >
      <van-grid-item
        icon="user-o"
        to="/patient/info"
        text="患者信息"
      />
      <van-grid-item
        icon="exchange"
        @click="isChangeHospital"
        text="切换医院"
      />
      <van-grid-item
        icon="calendar-o"
        to="/patient/punchClock"
        text="打卡"
      />
      <van-grid-item
        icon="description"
        text="我的用药"
      />
      <van-grid-item
        icon="todo-list-o"
        text="我的检查"
      />
      <van-grid-item
        icon="records"
        text="我的随访"
      />
      <van-grid-item
        icon="video-o"
        text="健康教育"
      />
      <van-grid-item
        icon="chat-o"
        text="在线咨询"
      />
    </van-grid>
  </van-row>
  <van-row style="margin: 10px 20px">
    <van-button
      round
      block
      plain
      hairline
      type="default"
      @click="logout"
      >退出登录
    </van-button>
  </van-row>
  <van-popup
    v-model:show="changeHospital"
    round
    position="bottom"
  >
    <van-picker
      title="选择医院"
      :columns="hospitals"
      :columns-field-names="hospitalFieldName"
      @cancel="onCancelChangeHospital"
      @confirm="onConfirmChangeHospital"
    />
  </van-popup>
</template>

<script setup lang="ts">
import { defineComponent, ref } from 'vue'
import { useRouter } from 'vue-router'
import { globalStore } from '@/store'

defineComponent({
  name: 'Patient'
})

const router = useRouter()
const global = globalStore()
const changeHospital = ref(false)
const hospitals = [
  { hospitalName: '重庆附二', id: '1' },
  { hospitalName: '重庆附一', id: '1' }
]
const hospitalFieldName = {
  text: 'hospitalName'
}

const onConfirmChangeHospital = (value: any) => {
  console.log(value.id)
}
const onCancelChangeHospital = () => {
  changeHospital.value = false
}
const isChangeHospital = () => {
  changeHospital.value = true
}
const logout = () => {
  router.replace('/login')
  globalStore().clear()
  console.log('退出登录')
}
</script>

<style scoped>
.patient_name_color {
  width: 100%;
  font-weight: bold;
  color: #2f3b48;
  font-size: large;
}

.hospital_name_color {
  width: 100%;
  font-weight: bold;
  color: #87919c;
  font-size: small;
}
</style>
