<template>
  <section>
    <ZySearchForm :formValue="state.query" @submit="goPage" @reset="handleReset">
      <a-form-item name="operator">
        <a-input
          v-model:value="state.query.keyword"
          allowClear
          placeholder="请输入请求路径"
          @pressEnter="goPage"
          autocomplete="off" />
      </a-form-item>
    </ZySearchForm>
    <a-table
      bordered
      :loading="state.loading"
      :columns="columns"
      :pagination="state.query"
      @change="pageChange"
      :row-class-name="(_record, index) => (index % 2 === 1 ? 'table-striped' : null)"
      :data-source="state.dataList">
      <template #bodyCell="{ index, column, record }">
        <template v-if="column.key === 'ind'">
          {{ index + 1 }}
        </template>
        <template v-else-if="column.key === 'httpMethod'">
          <a-tag color="green" v-if="record.httpMethod === 'GET'"> GET </a-tag>
          <a-tag color="blue" v-else-if="record.httpMethod === 'POST'"> POST </a-tag>
          <a-tag color="orange" v-else-if="record.httpMethod === 'PUT'"> PUT </a-tag>
          <a-tag color="red" v-else-if="record.httpMethod === 'DELETE'"> DELETE </a-tag>
          <a-tag color="purple" v-else-if="record.httpMethod === 'PATCH'"> PATCH </a-tag>
          <a-tag color="gold" v-else> {{ record.httpMethod }} </a-tag>
        </template>
        <template v-else-if="column.key === 'startTime'">
          {{ new Date(record.startTime).toLocaleString() }}
        </template>
        <template v-else-if="column.key === 'status'">
          <a-tag color="#87d068" v-if="record.status === 200"> 200 </a-tag>
          <a-tag color="orange" v-else> {{ record.status }} </a-tag>
        </template>
        <template v-else-if="column.key === 'action'">
          <a-space>
            <a-button type="link" @click="() => goView(record)">查看</a-button>
          </a-space>
        </template>
      </template>
    </a-table>
    <ZyModal
      :minWidth="650"
      :show="state.show.view"
      title="查看操作日志"
      key="ViewUsers_opt_logsInfo"
      @close="close">
      <ViewUsers_opt_logsInfo :viewData="state.viewData" @close="close" />
    </ZyModal>
  </section>
</template>

<script setup>
import { reactive, toRaw } from 'vue'
import ViewUsers_opt_logsInfo from './view-users_opt_logs-info.vue'
import ZyModal from 'comps/common/ZyModal.vue'
import ZySearchForm from 'comps/common/ZySearchForm.vue'
import { apiLogList } from 'api/modules/api.logs'

const columns = [
  { title: '#', width: 100, key: 'ind', align: 'center' },
  { title: '请求路径', dataIndex: 'apiPath', key: 'apiPath', align: 'center' },
  { title: '请求方法', dataIndex: 'httpMethod', key: 'httpMethod', align: 'center' },
  { title: '请求时间', dataIndex: 'startTime', key: 'startTime', align: 'center' },
  { title: '响应时长/ms', dataIndex: 'spendTime', key: 'spendTime', align: 'center' },
  { title: '响应状态', dataIndex: 'status', key: 'status', align: 'center' },
  { title: '远程IP', dataIndex: 'remoteAddr', key: 'remoteAddr', align: 'center' },
  { title: '操作', width: 225, key: 'action', align: 'center', fixed: 'right' },
]
const state = reactive({
  show: { view: false },
  // 暂存查看数据
  viewData: {},
  query: {
    current: 1,
    pageSize: 10,
    total: 0,
    showSizeChanger: true,
    showTotal: (total) => `共 ${total} 条数据`,
    keyword: '',
  },
  dataList: [],
  // loading
  loading: {
    spinning: false,
    tip: '加载中',
  },
})

// 查询
const goPage = (num = 1) => {
  state.query.current = num
  getDataList()
}
// 重置查询条件
const handleReset = () => {
  state.query.keyword = ''
  goPage()
}
// 分页
const pageChange = ({ current, pageSize }) => {
  state.query.current = current
  state.query.pageSize = pageSize
  getDataList()
}

// 加载数据
const getDataList = () => {
  state.loading.spinning = true
  let p = toRaw(state.query)

  apiLogList(p)
    .then((res) => {
      state.dataList = res.data.result
      state.query.total = res.data.total
      state.query.current = res.data.current
      state.query.pageSize = res.data.pageSize
    })
    .finally(() => {
      state.loading.spinning = false
    })
}

const goView = (row) => {
  state.show.view = true
  state.viewData = row
}

const close = () => {
  state.show.view = false
  goPage()
}

goPage()
</script>
