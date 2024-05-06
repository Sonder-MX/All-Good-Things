<template>
  <section class="zy-view">
    <ZyViewRow>
      <ZyViewItem label="邮箱">
        {{ viewData.email }}
      </ZyViewItem>
      <ZyViewItem label="用户名">
        {{ viewData.username }}
      </ZyViewItem>
    </ZyViewRow>
    <ZyViewRow>
      <ZyViewItem label="状态">
        <a-tag :color="viewData.isActive ? '#87d068' : 'orange'">
          {{ viewData.isActive ? '正常' : '禁用' }}
        </a-tag>
      </ZyViewItem>
      <ZyViewItem label="备注">暂无信息</ZyViewItem>
    </ZyViewRow>
    <ZyViewRow>
      <ZyViewItem label="创建时间">
        {{ new Date(viewData.created).toLocaleString() }}
      </ZyViewItem>
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

<style scoped></style>
