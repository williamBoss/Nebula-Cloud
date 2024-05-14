import { fetchEventSource } from '@microsoft/fetch-event-source'

export const thinkingInterval = (message: any) => {
  // 动态显示“思考中...”
  let dots = 0
  setInterval(() => {
    if (dots < 3) {
      message.think_content += '.'
      dots++
    } else {
      message.think_content = '思考中'
      dots = 0
    }
  }, 500)
}

export const fetchPrompt = (
  url: string,
  prompt: string,
  ctrl: AbortController,
  message: any,
  scrollToBottom: Function
) => {
  return fetchEventSource(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'User-Id': '1232123'
    },
    body: JSON.stringify({
      prompt: prompt
    }),
    openWhenHidden: true,
    credentials: 'include',
    signal: ctrl?.signal,
    mode: 'cors',
    onmessage: async (event: any) => {
      message.is_thinks = false
      const { data } = event
      const json_data = JSON.parse(data)
      let { content } = json_data.delta
      if (!content) {
        content = ''
      }
      message.content += content
      // 自动滚动到底部
      scrollToBottom()
    },
    onerror(err: any) {
      console.log('err', err)
      throw err
    },
    async onopen(response: any) {
      if (response.ok) {
        console.log('开始建立连接')
      }
    },
    onclose() {
      console.log('关闭')
    }
  })
}
