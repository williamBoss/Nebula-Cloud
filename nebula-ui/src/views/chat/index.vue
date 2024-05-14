<script setup lang="ts">
import { defineComponent, nextTick, onMounted, reactive, ref } from 'vue'
import defaultAvatar from '@/assets/images/profile.jpg'
import { CopyDocument, Refresh } from '@element-plus/icons-vue'
import { ElMessage, ElScrollbar } from 'element-plus'
import { fetchPrompt, thinkingInterval } from '@/views/chat/index.ts'

defineComponent({
  name: 'Chat'
})

const scrollbarRef = ref<InstanceType<typeof ElScrollbar>>()
const prompt = ref('')
// 示例数据结构：{ id: 1, content: 'Hello, World!', is_user: true }
const messages = ref<any[]>([])
const welcome_commands = ref<any[]>([])
const is_input = ref<boolean>(false)

const scrollToBottom = () => {
  nextTick(() => {
    if (scrollbarRef.value) {
      scrollbarRef.value!.setScrollTop(scrollbarRef.value.wrapRef!.scrollHeight)
    }
  })
}

const addMessage = (
  messages: any,
  is_thinks: boolean = false,
  content: string = '',
  is_user: boolean = false,
  is_welcome: boolean = false
) => {
  let message = reactive({
    id: messages.length + 1,
    is_thinks: is_thinks,
    think_content: '思考中',
    content: content,
    is_user: is_user,
    is_welcome: is_welcome
  })
  messages.push(message)
  return message
}

const welcome = () => {
  is_input.value = true
  let message = addMessage(messages.value, false, '', false, true)
  fetch('/dev-api/chat/welcome', { method: 'GET' })
    .then((response) => {
      return response.json()
    })
    .then((res) => {
      const { welcome_message, available_commands } = res
      message.content = welcome_message
      welcome_commands.value = available_commands
      // 自动滚动到底部
      scrollToBottom()
    })
    .catch((err) => {
      console.error(err)
    })
    .finally(() => {
      is_input.value = false
    })
}

const sendCommand = (command: string) => {
  prompt.value = command
  const event = new KeyboardEvent('keyup', {
    key: 'Enter',
    shiftKey: true
  })
  sendMessage(event)
}

const sendMessage = (event: KeyboardEvent) => {
  if (is_input.value) {
    ElMessage.warning('正在思考中，请稍后提问')
    return
  }
  is_input.value = true
  if (event instanceof KeyboardEvent) {
    if (event.key === 'Enter' && event.shiftKey) {
      handlePrompt()
    }
  } else {
    handlePrompt()
  }
}

const handlePrompt = () => {
  prompt.value = prompt.value.trim()
  if (prompt.value) {
    // 添加消息到列表
    addMessage(messages.value, false, prompt.value, true)
    scrollToBottom()
    // 发送消息请求ai对话
    sendPrompt(messages.value, prompt.value)
    // 清空输入框
    prompt.value = ''
  } else {
    ElMessage.warning('请输入需要咨询的内容')
    is_input.value = false
  }
}

const sendPrompt = (messages: any, prompt: string) => {
  let message = addMessage(messages, true, '', false)
  thinkingInterval(message)
  const ctrl = new AbortController()
  fetchPrompt('dev-api/chat/assistant/stream', prompt, ctrl, message, scrollToBottom)
    .catch((err: any) => {
      console.log({ err })
      throw new Error(err)
    })
    .finally(() => {
      is_input.value = false
    })
}

onMounted(() => {
  welcome()
})
</script>

<template>
  <div class="relative h-full max-w-full overflow-hidden">
    <div class="flex h-full flex-col">
      <div class="flex-1 h-90%">
        <el-scrollbar ref="scrollbarRef">
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
                      <div v-if="message.is_thinks">
                        {{ message.think_content }}
                      </div>
                      <div
                        class="text-3.75 whitespace-pre-wrap break-words"
                        v-else
                      >
                        {{ message.content }}
                        <div v-if="message.is_welcome">
                          <el-button
                            v-for="command in welcome_commands"
                            @click="sendCommand(command)"
                          >
                            {{ command }}
                          </el-button>
                        </div>
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
            @keyup.enter="sendMessage($event)"
          />
          <el-button
            type="primary"
            :loading="is_input"
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
