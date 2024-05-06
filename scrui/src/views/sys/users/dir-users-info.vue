<template>
  <section>
    <ZySearchForm :formValue="state.query" @submit="goPage" @reset="handleReset">
      <a-form-item name="name">
        <a-input
          v-model:value="state.query.keyword"
          allowClear
          placeholder="请输入用户名"
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
        <template v-else-if="column.key === 'isActice'">
          <a-tag color="#87d068" v-if="record.isActive"> 启 用 </a-tag>
          <a-tag color="orange" v-else> 禁 用 </a-tag>
        </template>
        <template v-else-if="column.key === 'remark'"> 暂无 </template>
        <template v-else-if="column.key === 'created'">
          {{ new Date(record.created).toLocaleString() }}
        </template>
        <template v-else-if="column.key === 'action'">
          <ZyToolButton
            :showView="true"
            @view="goView(record)"
            :showEdit="false"
            :showDelete="false">
          </ZyToolButton>
        </template>
      </template>
    </a-table>
    <ZyModal
      :minWidth="650"
      :minHeight="200"
      :show="state.show.view"
      title="查看用户"
      key="ViewUserInfo"
      @close="close">
      <ViewUserInfo :viewData="state.viewData" @close="close" />
    </ZyModal>
  </section>
</template>

<script setup>
import { reactive, toRaw } from 'vue'
import ZyModal from '../../../components/common/ZyModal.vue'
import ViewUserInfo from './view-users-info.vue'
import ZyToolButton from '../../../components/common/ZyToolButton.vue'
import ZySearchForm from '../../../components/common/ZySearchForm.vue'
import { usersList } from '../../../api/modules/api.users'

const columns = [
  { title: '#', width: 100, dataIndex: 'ind', key: 'ind', align: 'center' },
  { title: '用户名', dataIndex: 'username', key: 'username', align: 'center' },
  { title: '用户邮箱', dataIndex: 'email', key: 'email', align: 'center' },
  { title: '账号状态', dataIndex: 'isActice', key: 'isActice', align: 'center' },
  { title: '创建时间', dataIndex: 'created', key: 'created', align: 'center' },
  { title: '备注', dataIndex: 'remark', key: 'remark', align: '' },
  { title: '操作', width: 225, key: 'action', align: 'center', fixed: 'right' },
]

const state = reactive({
  show: {
    view: false,
  },
  expandedRowKeys: [],
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

const pageChange = ({ current, pageSize }) => {
  state.query.current = current
  state.query.pageSize = pageSize
  getDataList()
}

const getDataList = () => {
  state.loading.spinning = true
  // 将响应式query返回起原始对象
  let p = toRaw(state.query)
  usersList(p)
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

const close = (isSave) => {
  state.show.view = false
  isSave && goPage()
}

goPage()
</script>

<style scoped></style>
