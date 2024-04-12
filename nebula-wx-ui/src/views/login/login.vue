<template>
  <van-row
    justify="center"
    style="margin-top: 30px"
  >
    <van-image
      width="175px"
      :src="logo"
    />
  </van-row>
  <van-row
    justify="center"
    style="margin-top: 20px"
  >
    慢病随访平台
  </van-row>
  <van-form
    @submit="onBind"
    style="margin-top: 10%"
  >
    <van-cell-group inset>
      <van-row
        justify="center"
        style="margin: 20px; position: relative"
      >
        <van-col
          span="8"
          style="text-align: center"
        >
          <van-image
            round
            fit="cover"
            position="center"
            width="50px"
            height="50px"
            :src="heardImg"
          />
        </van-col>
      </van-row>
      <van-row justify="center">
        {{ userName }}
      </van-row>
      <van-field
        v-model="phone"
        name="phone"
        left-icon="phone"
        placeholder="手机号"
        :rules="[{ required: true, message: '请填写手机号' }]"
      />
      <div style="margin: 20px">
        <van-button
          round
          block
          type="primary"
          native-type="submit"
        >
          绑定
        </van-button>
      </div>
      <van-row
        justify="center"
        style="margin: 10px"
      >
        <van-button
          class="no-border"
          plain
          type="primary"
          @click="onRegister"
          >注册新用户
        </van-button>
      </van-row>
    </van-cell-group>
  </van-form>
</template>

<script setup lang="ts">
import { WechatService } from '@/apis/api'
import { defineComponent, ref } from 'vue'
import { showDialog, showNotify } from 'vant'
import logo from '@/assets/logo/logo.png'
import { useRouter } from 'vue-router'
import { HOME_URL } from '@/config/config.ts'
import { globalStore } from '@/store'

defineComponent({
  name: 'Login'
})

const router = useRouter()
const code = WechatService.wechat.getCode() as string
const userName = ref('用户名')
const heardImg = ref('')
const phone = ref('')
const redirect = sessionStorage.getItem('wxRedirectUrl') || '/'
// 跳转到授权前访问的页面
router.replace(redirect)

// 3.如果有code，则已经授权
if (code) {
  WechatService.wechat
    .wechatUserInfo(code)
    .then((data: any) => {
      if (data) {
        //将一些信息存储到本地
        globalStore().initWechatUserInfo(data)
        globalStore().token = data.openid
        userName.value = data.nickName
        heardImg.value = data.headImgUrl
      }
    })
    .then(() => {
      const redirect = sessionStorage.getItem('wxRedirectUrl') || '/'
      // 跳转到授权前访问的页面
      router.replace(redirect)
    })
} else {
  // 2.跳转授权
  const callbackUrl = window.location.origin + window.location.pathname
  WechatService.wechat.auth(callbackUrl)
}

const onBind = (values: string) => {
  router.replace(HOME_URL)
  console.log('submit', values)
}

const onRegister = () => {
  showDialog({
    message: '请联系医生进行患者信息注册。'
  })
}
</script>

<style scoped>
.no-border {
  border: none;
}
</style>
