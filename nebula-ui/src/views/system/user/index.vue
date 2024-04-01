<template>
  <div
    v-loading.fullscreen.lock="fullLoading"
    class="flx"
    style="height: 100%"
  >
    <div class="left-panel">
      <TreeFilter
        :data="dict.deptOptions"
        :label="'deptName'"
        :id="'deptId'"
        :default-value="initParam.deptId"
        @change="changeInitParam"
      />
    </div>
    <div class="right-panel">
      <div class="table">
        <el-card
          v-show="isShowSearch"
          class="table-search card rounded is-plain"
          shadow="never"
        >
          <el-form
            ref="queryFormRef"
            :model="state.searchParam"
            :inline="true"
            label-width="68px"
          >
            <el-form-item
              label="登录账号"
              prop="userName"
            >
              <el-input
                v-model="state.searchParam.userName"
                placeholder="请输入登陆账号"
                clearable
                style="width: 240px"
                @keyup.enter="handleQuery"
              />
            </el-form-item>
            <el-form-item
              label="手机号码"
              prop="phonenumber"
            >
              <el-input
                v-model="state.searchParam.phonenumber"
                placeholder="请输入手机号码"
                clearable
                style="width: 240px"
                @keyup.enter="handleQuery"
              />
            </el-form-item>
            <el-form-item
              label="状态"
              prop="status"
            >
              <el-select
                v-model="state.searchParam.status"
                placeholder="用户状态"
                :clearable="true"
                style="width: 240px"
              >
                <el-option
                  v-for="statusOption in dict.statusOptions"
                  :key="statusOption.dictValue"
                  :label="statusOption.dictLabel"
                  :value="statusOption.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item
              label="创建时间"
              prop="dateRange"
            >
              <el-date-picker
                v-model="state.searchParam.dateRange"
                style="width: 220px"
                value-format="YYYY-MM-DD"
                type="daterange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              />
            </el-form-item>
          </el-form>
          <div>
            <el-button
              type="primary"
              :icon="Search"
              @click="handleQuery"
            >
              搜索
            </el-button>
            <el-button
              type="info"
              plain
              :icon="Refresh"
              @click="resetQuery"
            >
              重置
            </el-button>
          </div>
        </el-card>
        <el-card
          class="card rounded is-plain"
          shadow="never"
        >
          <div class="flx-justify-between table-operation">
            <div>
              <el-button
                type="primary"
                :icon="Plus"
                @click="handleAdd"
              >
                新增用户
              </el-button>
            </div>
            <div>
              <el-button
                :icon="Refresh"
                circle
                @click="handleQuery"
              />
              <el-button
                :icon="Search"
                circle
                @click="isShowSearch = !isShowSearch"
              />
            </div>
          </div>
          <el-table
            v-loading="loading"
            :data="state.pageable.records"
            header-row-class-name="table-header-row-class"
            header-cell-class-name="table-header-cell-class"
          >
            <template #empty>
              <div class="table-empty">
                <div>暂无数据</div>
              </div>
            </template>
            <el-table-column
              key="userName"
              label="登录账号"
              align="center"
              prop="userName"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              key="nickName"
              label="用户名称"
              align="center"
              prop="nickName"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              key="deptName"
              label="部门名称"
              align="center"
              prop="deptName"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              key="phonenumber"
              label="手机号码"
              align="center"
              prop="phonenumber"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              key="status"
              label="用户状态"
              width="160"
              align="center"
              prop="status"
            >
              <template #default="scope">
                <el-switch
                  :model-value="scope.row.status"
                  :active-text="scope.row.status === '0' ? '启用' : '禁用'"
                  :active-value="'0'"
                  :inactive-value="'1'"
                  @change="changeStatus($event, scope.row)"
                />
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              align="center"
              :fixed="`right`"
              width="210"
              class-name="small-padding fixed-width"
            >
              <template #default="scope">
                <el-button
                  type="primary"
                  size="small"
                  link
                  :icon="EditPen"
                  @click="handleUpdate(scope.row)"
                  >编辑
                </el-button>
                <el-button
                  type="primary"
                  size="small"
                  link
                  :icon="Refresh"
                  @click="handleResetPwd(scope.row)"
                  >重置密码
                </el-button>
                <el-button
                  type="danger"
                  size="small"
                  link
                  :icon="Delete"
                  @click="handleDelete(scope.row)"
                  >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            :current-page="parseInt(state.pageable.pageNumber)"
            :page-size="parseInt(state.pageable.pageSize)"
            :page-sizes="[10, 25, 50, 100]"
            background
            layout="->, total, sizes, prev, pager, next"
            :total="parseInt(state.pageable.totalRow)"
            style="margin-top: 20px"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </el-card>
      </div>
    </div>
    <UserInfo
      ref="userRef"
      :title="title"
      :sex-options="dict.sexOptions"
      :status-options="dict.statusOptions"
      :dept-options="dict.deptOptions"
      @refresh="handleQuery"
    />
  </div>
</template>

<script setup lang="ts">
import { defineComponent, onBeforeMount, reactive, ref, watch } from 'vue'
import { ConfigService, DeptService, UserService } from '@/api/sys-api.js'
import { Delete, EditPen, Plus, Refresh, Search } from '@element-plus/icons-vue'
import { addDateRange, resetForm } from '@/utils/util'
import { ElMessage as elMessage } from 'element-plus'
import UserInfo from '@/views/system/user/dialog/userInfo.vue'
import { useConfirmHandle } from '@/hooks/useConfirmHandle.ts'
import { usePromptHandle } from '@/hooks/usePromptHandle.ts'
import { Config, ConfigValue, Dept, Pageable, User } from '@api/types/sys-api'
import TreeFilter from '@components/TreeFilter/index.vue'

defineComponent({
  name: 'SysUser'
})

interface InitParam {
  deptId: string | null
}

interface Dict {
  statusOptions: ConfigValue[]
  sexOptions: ConfigValue[]
  deptOptions: Dept[]
}

interface State {
  pageable: Pageable<User>
  searchParam: Record<string, any>
  searchInitParam: Record<string, any>
  totalParam: Record<string, any>
}

const queryFormRef = ref()
const initParam = reactive<InitParam>({ deptId: null })
// 是否展示搜索模块
const isShowSearch = ref(true)
const loading = ref(false)
const fullLoading = ref(false)
const title = ref('')
const userRef = ref()
const dict = reactive<Dict>({
  // 状态数据字典
  statusOptions: [],
  // 性别状态字典
  sexOptions: [],
  deptOptions: []
})
const state = reactive<State>({
  // 分页数据
  pageable: {
    // 表格数据
    records: [] as User[],
    // 当前页数
    pageNumber: '1',
    // 每页显示条数
    pageSize: '10',
    // 总条数
    totalRow: '0'
  },
  // 查询参数(只包括查询)
  searchParam: {},
  // 初始化默认的查询参数
  searchInitParam: {},
  // 总参数(包含分页和查询参数)
  totalParam: {}
})

// 树形筛选切换
const changeInitParam = (val: string) => {
  initParam.deptId = val
}
/** 搜索按钮操作 */
const handleQuery = () => {
  state.pageable.pageNumber = '1'
  getUserList()
}
/** 重置按钮操作 */
const resetQuery = () => {
  initParam.deptId = null
  Object.assign(state.searchParam, { deptId: initParam.deptId })
  resetForm(queryFormRef.value)
  getUserList()
}
/** 查询用户列表 */
const getUserList = () => {
  const { records, ...pageParam } = state.pageable
  Object.assign(state.totalParam, state.searchParam, pageParam)
  UserService.user.getList(addDateRange(state.totalParam)).then((data: Pageable<User>) => {
    state.pageable.records = data.records
    state.pageable.totalRow = data.totalRow
  })
}
const changeStatus = async (val: number, params: any) => {
  await useConfirmHandle(
    UserService.user.changeUserStatus,
    {
      userId: params.userId,
      status: val
    },
    `切换【${params.nickName}】用户状态`
  )
  getUserList()
}
const handleAdd = () => {
  fullLoading.value = true
  title.value = '添加用户'
  userRef.value.reset()
  UserService.user.getInfoById('').then((data: User) => {
    userRef.value.acceptParams(data)
    fullLoading.value = false
  })
}
const handleUpdate = (row: any) => {
  fullLoading.value = true
  title.value = '修改用户'
  userRef.value.reset()
  UserService.user.getInfoById(row.userId).then((data: User) => {
    userRef.value.acceptParams(data)
    fullLoading.value = false
  })
}
const handleResetPwd = (row: any) => {
  usePromptHandle('请输入"' + row.userName + '"的新密码').then(({ value }: any) => {
    UserService.user.resetUserPwd(row.userId, value).then(() => {
      elMessage.success('修改成功，新密码是：' + value)
    })
  })
}
const handleDelete = (row: any) => {
  useConfirmHandle(UserService.user.del, row.userId, `删除用户编号为${row.userId}的数据项`).then(() => {
    getUserList()
  })
}
/**
 * @description 每页条数改变
 * @param val 当前条数
 * @return
 * */
const handleSizeChange = (val: string) => {
  state.pageable.pageNumber = '1'
  state.pageable.pageSize = val
  getUserList()
}
/**
 * @description 当前页改变
 * @param val 当前页
 * @return
 * */
const handleCurrentChange = (val: string) => {
  state.pageable.pageNumber = val
  getUserList()
}

onBeforeMount(() => {
  ConfigService.config.getConfigByKey('sys_status').then((data: Config) => {
    dict.statusOptions = JSON.parse(data.configValue)
  })
  ConfigService.config.getConfigByKey('sys_user_sex').then((data: Config) => {
    dict.sexOptions = JSON.parse(data.configValue)
  })
  DeptService.dept.getDeptTreeSelect().then((data: Dept[]) => {
    dict.deptOptions = data
  })
  getUserList()
})

watch(
  () => initParam.deptId,
  (newValue) => {
    if (newValue) {
      Object.assign(state.searchParam, { deptId: newValue })
      getUserList()
    }
  }
)
</script>

<style scoped>
.left-panel {
  width: calc(220px + var(--size-2));
}

.right-panel {
  width: calc(100% - calc(220px + var(--size-2)));
}
</style>
