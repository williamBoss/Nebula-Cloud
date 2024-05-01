<script setup lang="ts">
import { defineComponent } from 'vue'

defineComponent({
  name: 'Check'
})

const onClick = () => {}
</script>

<template>
  <div class="mt-16px">
    <van-space
      direction="vertical"
      fill
    >
      <van-cell-group inset>
        <div class="time">今天 16:19</div>
        <div
          class="content"
          style="justify-content: space-between"
        >
          <div>
            <span class="check-project">高血压</span>
            <van-tag
              type="danger"
              round
            >
              高
            </van-tag>
          </div>
          <div>
            <div>高压： 150mg/mmHg</div>
            <div>低压： 60mg/mmHg</div>
          </div>
        </div>
      </van-cell-group>
      <van-cell-group
        class="bg-color"
        inset
      >
        <div class="time">今天 12:19</div>
        <div
          class="content"
          style="justify-content: space-between"
        >
          <div>
            <span class="check-project">心跳</span>
          </div>
          <div>
            <div>75次/min</div>
          </div>
        </div>
      </van-cell-group>
    </van-space>
  </div>
  <van-floating-bubble
    icon="plus"
    @click="onClick"
  />
</template>

<style scoped>
.van-cell-group {
  min-height: 70px;
  background: linear-gradient(to bottom right, #38cbfb, #5e66f8);
  color: #fff;
  padding: 20px;
}

.bg-color {
  background: linear-gradient(to bottom right, #fdac37, #f3312f);
}

.time {
  font-size: 24px;
  font-weight: bold;
}

.content {
  display: flex;
  margin-top: 10px;
}

.content .check-project {
  margin: 5px;
}
</style>
