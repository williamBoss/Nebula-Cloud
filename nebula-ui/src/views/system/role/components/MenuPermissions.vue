<script setup lang="tsx">
import { defineComponent, onMounted, ref } from 'vue'
import { Menu } from '@api/types/sys-api'
import { MenuService, RoleService } from '@api/sys-api.ts'
import { ElMessage } from 'element-plus'

defineComponent({
  name: 'MenuPermissions'
})

interface Props {
  roleKey: string
}

const props = defineProps<Props>()
const checkedMenus = ref<Array<number | string>>([])
const menuList = ref<Menu[]>([])

const RoleTreeMenu = (props: { menu: Menu; index: number }) => (
  <div style={{ marginLeft: `${props.index * 4}%` }}>
    <el-checkbox
      key={props.menu.menuId}
      label={props.menu.menuId}
      onChange={(isCheck: boolean) => selectMenus(isCheck, props.menu.menuId)}
    >
      {props.menu.menuName}
    </el-checkbox>
    {props.menu.children &&
      props.menu.children.some((e) => e.menuType == 'C') &&
      props.menu.children.map((child) => RoleTreeMenu({ menu: child, index: props.index + 1 }))}
    {props.menu.children && props.menu.children.some((e) => e.menuType == 'F') && RoleTreePoint(props.menu.children)}
  </div>
)
const RoleTreePoint = (menu: Menu[]) => {
  return (
    <div style={{ marginLeft: `4%` }}>
      {menu.map((child) => (
        <el-checkbox
          key={child.menuId}
          label={child.menuId}
          onChange={(isCheck: boolean) => selectMenus(isCheck, child.menuId)}
        >
          {child.menuName}
        </el-checkbox>
      ))}
    </div>
  )
}

const findMenu = (menus: Menu[], menuId: number | string): Menu | undefined => {
  for (const menu of menus) {
    if (menu.menuId === menuId) {
      return menu
    }
    // 在子菜单中递归查找
    const found = menu.children && menu.children.length && findMenu(menu.children, menuId)
    if (found) {
      return found
    }
  }
  // 如果没有找到匹配的菜单，返回 undefined
  return undefined
}
const selectMenus = (isCheck: boolean, menuId: number | string) => {
  if (!menuId) {
    return
  }
  const menu = findMenu(menuList.value, menuId)!
  // 选中
  if (isCheck) {
    // 选中上级
    selectUpperLevel(menu)
    // 选中本级以及子级
    addCheckedDataAndChildren(menu)
    // 是否有关联菜单 有则选中
    // menu.contextMenuId && addCheckedData(menu.contextMenuId)
  } else {
    // 取消选中本级以及子级
    deleteCheckedDataAndChildren(menu)
  }
}
const selectUpperLevel = (menu: Menu) => {
  // 拿到上级key
  let parentId = menu.parentId
  if (!parentId) {
    return
  }
  // 从权限树对象 获取该父级对象
  let parentModule = findMenu(menuList.value, parentId)
  if (!parentModule) {
    return
  }
  // 选中父级
  let parentIndex = checkedMenus.value.findIndex((e) => parentModule?.menuId === e)
  parentModule.menuId && parentIndex === -1 && addCheckedData(parentModule.menuId)
  // 如果上级还有上级 则进行递归
  parentModule.parentId && selectUpperLevel(parentModule)
}
const addCheckedData = (menuId: number | string) => {
  !checkedMenus.value.some((e) => e === menuId) && checkedMenus.value.push(menuId)
}
const deleteCheckedData = (index: number) => {
  checkedMenus.value.splice(index, 1)
}
const addCheckedDataAndChildren = (menu: Menu) => {
  let findIndex = checkedMenus.value.findIndex((val: number | string) => val === menu.menuId)
  menu.menuId && findIndex === -1 && addCheckedData(menu.menuId)
  menu.children?.forEach((item) => {
    addCheckedDataAndChildren(item)
  })
}
const deleteCheckedDataAndChildren = (menu: Menu) => {
  let findIndex = checkedMenus.value.findIndex((val: number | string) => val === menu.menuId)
  findIndex !== -1 && deleteCheckedData(findIndex)
  menu.children?.forEach((item) => {
    deleteCheckedDataAndChildren(item)
  })
}
const getCheckMenus = () => {
  RoleService.role.getCheckMenuByRole(props.roleKey).then((data: string[]) => {
    checkedMenus.value = data
  })
}
const handleSave = () => {
  RoleService.role.addMenuRole(props.roleKey, checkedMenus.value).then(() => {
    ElMessage.success('保存成功')
    getCheckMenus()
  })
}

onMounted(() => {
  MenuService.menu.getAllList().then((data: Menu[]) => {
    menuList.value = data
  })
  getCheckMenus()
})
</script>

<template>
  <div>
    <el-text
      class="flx-justify-between"
      style="margin: var(--size-3) 0"
    >
      设置角色对应的后台菜单、功能操作权限
      <el-button
        type="primary"
        @click="handleSave"
      >
        保 存
      </el-button>
    </el-text>
    <el-checkbox-group v-model="checkedMenus">
      <div>
        <ul>
          <!--li 菜单模块 start-->
          <li
            v-for="menu in menuList"
            :key="menu.menuId"
          >
            <RoleTreeMenu
              :menu
              :index="0"
            />
          </li>
          <!--li 菜单模块 end-->
        </ul>
      </div>
    </el-checkbox-group>
  </div>
</template>

<style scoped>
ul {
  padding: 0 15px;
}

li {
  list-style: none;
  padding: 0;
  margin: 10px 0;
}
</style>
