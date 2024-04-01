<template>
  <template
    v-for="subItem in menuList"
    :key="subItem.path"
  >
    <el-sub-menu
      v-if="subItem.children && subItem.children.length > 0 && !subItem.meta.hidden"
      :index="subItem.name"
    >
      <template #title>
        <el-icon v-if="subItem.meta.iconType">
          <svg-icon
            v-if="subItem.meta.iconType === 'sl'"
            :name="subItem.meta.icon"
          />
          <component
            :is="resolveIcon(subItem.meta.icon)"
            v-else-if="subItem.meta.iconType === 'el'"
          />
        </el-icon>
        <span>{{ subItem.meta.title }}</span>
      </template>
      <SubMenu :menu-list="subItem.children" />
    </el-sub-menu>
    <el-menu-item
      v-else-if="!subItem.meta.hidden"
      :index="subItem.name"
      @click="handleClickMenu(subItem)"
    >
      <el-icon>
        <svg-icon
          v-if="subItem.meta.iconType === 'sl'"
          :name="subItem.meta.icon"
        />
        <component
          :is="resolveIcon(subItem.meta.icon)"
          v-else-if="subItem.meta.iconType === 'el'"
        />
      </el-icon>
      <template #title>
        <span>{{ subItem.meta.title }}</span>
      </template>
    </el-menu-item>
  </template>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import SubMenu from '@/layout/sidebar/subMenu/index.vue'
import * as Icons from '@element-plus/icons-vue'
import { defineComponent, toRefs } from 'vue'

defineComponent({
  name: 'SubMenu'
})

interface MenuProps {
  menuList: Array<any> // 你可以替换 any 为具体的类型
}

const props = defineProps<MenuProps>()
const { menuList } = toRefs(props)
const router = useRouter()

const handleClickMenu = (subItem: any) => {
  router.push(subItem.path)
}

const resolveIcon = (iconName: string) => {
  return Icons[iconName as keyof typeof Icons] || null
}
</script>

<style scoped>
li.el-menu-item {
  transition: none;
}

li.el-menu-item.is-active {
  background-color: #f4f6fb;
  border-top-left-radius: 50px;
  border-bottom-left-radius: 50px;
}

li.el-menu-item.is-active::before {
  content: '';
  position: absolute;
  top: -20px;
  right: 0;
  width: 20px;
  height: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 5px 5px 0 5px #f4f6fb;
  background-color: transparent;
  z-index: 999;
}

li.el-menu-item.is-active::after {
  content: '';
  position: absolute;
  bottom: -20px;
  right: 0;
  width: 20px;
  height: 20px;
  border-top-right-radius: 20px;
  box-shadow: 5px -5px 0 5px #f4f6fb;
  background-color: transparent;
  z-index: 999;
}
</style>
