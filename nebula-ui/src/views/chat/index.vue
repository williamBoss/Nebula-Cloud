<script setup lang="ts">
import { defineComponent, ref } from 'vue'
import defaultAvatar from '@/assets/images/profile.jpg'
import { CopyDocument, Refresh } from '@element-plus/icons-vue'

defineComponent({
  name: 'Chat'
})

const prompt = ref('')
// 示例数据结构：{ id: 1, content: 'Hello, World!', is_user: true }
const messages = ref<any[]>([])

const sendMessage = (event) => {
  if (event.key === 'Enter' && event.shiftKey) {
    if (prompt.value.trim()) {
      // 添加消息到列表
      messages.value.push({
        id: messages.value.length + 1,
        content: prompt.value,
        is_user: true
      })

      // 清空输入框
      prompt.value = ''

      // 发送消息请求ai对话
    }
  }
}
</script>

<template>
  <div class="relative h-full max-w-full overflow-hidden">
    <div class="flex h-full flex-col">
      <div class="flex-1 h-90%">
        <el-scrollbar>
          <div class="p-0">
            <div class="h-1.5"></div>
            <div class="w-full pb-9">
              <!--chat start-->
              <div
                class="flex flex-1 py-2 px-3 text-base mx-auto gap-3"
                v-for="message in messages"
                :key="message.id"
              >
                <div
                  class="pt-0.5 rounded-full"
                  v-if="!message.is_user"
                >
                  <img
                    alt="User"
                    loading="lazy"
                    width="24"
                    height="24"
                    class="rounded-sm"
                    :src="defaultAvatar"
                    style="color: transparent"
                  />
                </div>
                <div class="flex flex-col w-full min-w-0">
                  <div :class="['flex flex-col w-full gap-1 rtl:items-start', { 'items-end': message.is_user }]">
                    <div :class="['relative max-w-[90%] rounded-3xl', { 'px-5 py-2.5 bg-#e0e0e0': message.is_user }]">
                      <div class="text-3.75 whitespace-pre-wrap break-words">
                        {{ message.content }}
                      </div>
                    </div>
                    <div
                      class="flex mt-8px h-7 gap-[2px] text-gray-5"
                      v-if="!message.is_user"
                    >
                      <el-icon class="mr-10px">
                        <CopyDocument />
                      </el-icon>
                      <el-icon>
                        <Refresh />
                      </el-icon>
                    </div>
                  </div>
                </div>
              </div>
              <!--chat end-->
            </div>
          </div>
        </el-scrollbar>
      </div>
      <div class="flex flex-col gap-1.5 rounded-[26px] p-1.5 m-10px m-b-30px transition-colors bg-[#e0e0e0ab]">
        <div class="flex flex-items-end p-2">
          <el-input
            class="flex-grow m-r-10px"
            type="textarea"
            v-model="prompt"
            placeholder="输入消息..."
            :autosize="{ minRows: 1, maxRows: 9 }"
            @keyup.enter.native="sendMessage($event)"
          />
          <el-button
            type="primary"
            @click="sendMessage"
          >
            发送
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.el-textarea :deep(.el-textarea__inner) {
  font-size: 1rem;
}
</style>
