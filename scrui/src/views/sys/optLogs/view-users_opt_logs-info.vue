<template>
  <section class="zy-view">
    <ZyViewRow>
      <ZyViewItem label="请求路径">{{ viewData.apiPath }}</ZyViewItem>
    </ZyViewRow>

    <ZyViewRow>
      <ZyViewItem label="请求方法">
        <a-tag color="green" v-if="viewData.httpMethod === 'GET'"> GET </a-tag>
        <a-tag color="blue" v-else-if="viewData.httpMethod === 'POST'"> POST </a-tag>
        <a-tag color="orange" v-else-if="viewData.httpMethod === 'PUT'"> PUT </a-tag>
        <a-tag color="red" v-else-if="viewData.httpMethod === 'DELETE'"> DELETE </a-tag>
        <a-tag color="purple" v-else-if="viewData.httpMethod === 'PATCH'"> PATCH </a-tag>
        <a-tag color="gold" v-else> {{ viewData.httpMethod }} </a-tag>
      </ZyViewItem>
    </ZyViewRow>

    <ZyViewRow>
      <ZyViewItem label="请求时间">{{ new Date(viewData.startTime).toLocaleString() }}</ZyViewItem>
    </ZyViewRow>

    <ZyViewRow>
      <ZyViewItem label="请求耗时">{{ viewData.spendTime }}ms</ZyViewItem>
    </ZyViewRow>

    <ZyViewRow>
      <ZyViewItem label="响应状态">
        <a-tag color="#87d068" v-if="viewData.status === 200"> 200 响应成功 </a-tag>
        <a-tag color="orange" v-else> {{ viewData.status }} 响应失败</a-tag>
      </ZyViewItem>
    </ZyViewRow>

    <ZyViewRow>
      <ZyViewItem label="远程IP">{{ viewData.remoteAddr }}</ZyViewItem>
    </ZyViewRow>

    <ZyViewRow>
      <ZyViewItem label="备注">暂无</ZyViewItem>
    </ZyViewRow>
  </section>
</template>

<script setup>
import ZyViewRow from 'comps/common/ZyViewRow.vue'
import ZyViewItem from 'comps/common/ZyViewItem.vue'

const props = defineProps({
  viewData: {
    type: Object,
    default: () => {},
  },
})
const emit = defineEmits(['close'])
</script>

<style lang="scss" scoped></style>
