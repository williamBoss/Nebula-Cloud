<template>
  <div class="footer flx-center">
    <a
      href="/privacy-policy"
      target="_blank"
    >
      2024 © XXX公司，保留所有权利。
    </a>
    <a
      href="/privacy-policy"
      target="_blank"
    >
      ICP备xxxx号
    </a>
  </div>
</template>

<script setup lang="ts">
import { defineComponent } from 'vue'

defineComponent({
  name: 'PageFooter'
})
</script>

<style scoped>
.footer {
  padding: 0 0 8px;
}

.footer a {
  font-size: 14px;
  color: #858585;
  text-decoration: none;
  letter-spacing: 1px;
}
</style>
