<script lang="ts" setup>
import { ArrowRight, CaretBottom, CaretTop, Warning } from '@element-plus/icons-vue'
import BaseLine from '@components/ECharts/Line/BaseLine.vue'
import { ref } from 'vue'
import BasePie from '@components/ECharts/Pie/BasePie.vue'

const line = ref({
  legend: {
    data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine']
  },
  xAxis: {
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
  },
  series: [
    {
      name: 'Email',
      type: 'line',
      stack: 'Total',
      data: [120, 132, 101, 134, 90, 230, 210]
    },
    {
      name: 'Union Ads',
      type: 'line',
      stack: 'Total',
      data: [220, 182, 191, 234, 290, 330, 310]
    },
    {
      name: 'Video Ads',
      type: 'line',
      stack: 'Total',
      data: [150, 232, 201, 154, 190, 330, 410]
    },
    {
      name: 'Direct',
      type: 'line',
      stack: 'Total',
      data: [320, 332, 301, 334, 390, 330, 320]
    },
    {
      name: 'Search Engine',
      type: 'line',
      stack: 'Total',
      data: [820, 932, 901, 934, 1290, 1330, 1320]
    }
  ]
})
const pie = ref({
  series: [
    {
      data: [
        { value: 40, name: 'rose 1' },
        { value: 38, name: 'rose 2' },
        { value: 32, name: 'rose 3' },
        { value: 30, name: 'rose 4' },
        { value: 28, name: 'rose 5' },
        { value: 26, name: 'rose 6' },
        { value: 22, name: 'rose 7' },
        { value: 18, name: 'rose 8' }
      ]
    }
  ]
})
</script>

<template>
  <div>
    <el-row :gutter="16">
      <el-col :span="6">
        <div class="statistic-card">
          <el-statistic :value="98500">
            <template #title>
              <div style="display: inline-flex; align-items: center">
                Daily active users
                <el-tooltip
                  effect="dark"
                  content="Number of users who logged into the product in one day"
                  placement="top"
                >
                  <el-icon
                    style="margin-left: 4px"
                    :size="12"
                  >
                    <Warning />
                  </el-icon>
                </el-tooltip>
              </div>
            </template>
          </el-statistic>
          <div class="statistic-footer">
            <div class="footer-item">
              <span>than yesterday</span>
              <span class="green">
                24%
                <el-icon>
                  <CaretTop />
                </el-icon>
              </span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="statistic-card">
          <el-statistic :value="693700">
            <template #title>
              <div style="display: inline-flex; align-items: center">
                Monthly Active Users
                <el-tooltip
                  effect="dark"
                  content="Number of users who logged into the product in one month"
                  placement="top"
                >
                  <el-icon
                    style="margin-left: 4px"
                    :size="12"
                  >
                    <Warning />
                  </el-icon>
                </el-tooltip>
              </div>
            </template>
          </el-statistic>
          <div class="statistic-footer">
            <div class="footer-item">
              <span>month on month</span>
              <span class="red">
                12%
                <el-icon>
                  <CaretBottom />
                </el-icon>
              </span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="statistic-card">
          <el-statistic
            :value="72000"
            title="New transactions today"
          >
            <template #title>
              <div style="display: inline-flex; align-items: center">New transactions today</div>
            </template>
          </el-statistic>
          <div class="statistic-footer">
            <div class="footer-item">
              <span>than yesterday</span>
              <span class="green">
                16%
                <el-icon>
                  <CaretTop />
                </el-icon>
              </span>
            </div>
            <div class="footer-item">
              <el-icon :size="14">
                <ArrowRight />
              </el-icon>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="statistic-card">
          <el-statistic
            :value="72000"
            title="New transactions today"
          >
            <template #title>
              <div style="display: inline-flex; align-items: center">New transactions today</div>
            </template>
          </el-statistic>
          <div class="statistic-footer">
            <div class="footer-item">
              <span>than yesterday</span>
              <span class="green">
                16%
                <el-icon>
                  <CaretTop />
                </el-icon>
              </span>
            </div>
            <div class="footer-item">
              <el-icon :size="14">
                <ArrowRight />
              </el-icon>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <div style="margin-top: var(--size-3)">
      <el-card
        shadow="never"
        class="card rounded is-plain"
      >
        <el-tabs>
          <el-tab-pane
            label="User"
            name="first"
            >User</el-tab-pane
          >
          <el-tab-pane
            label="Config"
            name="second"
            >Config</el-tab-pane
          >
          <el-tab-pane
            label="Role"
            name="third"
            >Role</el-tab-pane
          >
          <el-tab-pane
            label="Task"
            name="fourth"
            >Task</el-tab-pane
          >
        </el-tabs>
      </el-card>
    </div>
    <div style="margin-top: var(--size-3)">
      <el-row :gutter="16">
        <el-col :span="16">
          <el-card
            shadow="never"
            class="card rounded is-plain"
          >
            <div class="title">会诊次数</div>
            <base-line :option="line" />
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card
            shadow="never"
            class="card rounded is-plain"
          >
            <div class="title">数据分析</div>
            <base-pie :option="pie" />
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped>
:global(h2#card-usage ~ .example .example-showcase) {
  background-color: var(--el-fill-color) !important;
}

.el-statistic {
  --el-statistic-content-font-size: 28px;
}

.statistic-card {
  padding: 20px;
  border-radius: 4px;
  background-color: var(--el-bg-color-overlay);
}

.statistic-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  font-size: 12px;
  color: var(--el-text-color-regular);
  margin-top: 16px;
}

.statistic-footer .footer-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.statistic-footer .footer-item span:last-child {
  display: inline-flex;
  align-items: center;
  margin-left: 4px;
}

.green {
  color: var(--el-color-success);
}
.red {
  color: var(--el-color-error);
}
</style>
