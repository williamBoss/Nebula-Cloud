<script setup lang="ts">
import {ref} from 'vue'

const username = ref('')
const password = ref('')
const login = () => {
  console.log("登录信息：", username.value, password.value);
}
</script>

<template>
  <view class="container">
    <view class="input-group">
      <text>用户名:</text>
      <input type="text" placeholder="请输入用户名" v-model="username" />
    </view>
    <view class="input-group">
      <text>密码:</text>
      <input type="password" placeholder="请输入密码" v-model="password" />
    </view>
    <button @click="login">登录</button>
  </view>
</template>

<style scoped>
.container {
  padding: 20px;
}
.input-group {
  margin-bottom: 10px;
}
</style>
