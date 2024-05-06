<template>
  <div class="gutter-example">
    <a-row :gutter="20">
      <a-col class="gutter-row" :span="8" v-for="pan in panelData">
        <a-card :title="pan.title" :hoverable="true" :headStyle :bodyStyle>
          <template #extra>
            <span style="color: #339af0" @click="handlePanelCopy(pan)">Copy</span>
          </template>
          <div v-if="pan.content">{{ pan.content }}</div>
          <a-spin v-if="!pan.content" />
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { ZyMessage } from '../../libs/util.toast'

let ws = null
const headStyle = {
  background: '#bbefdeaf',
  padding: '0 20px',
}

const bodyStyle = {
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  height: '120px',
  fontSize: '1.6rem',
  fontWeight: 'bold',
  color: '#88c7b1',
}

const panelData = ref([
  {
    title: '最后更新',
    content: '',
  },
  {
    title: '用户总数',
    content: 0,
  },
  {
    title: '司机总数',
    content: 0,
  },
  {
    title: '订单总数',
    content: 0,
  },
  {
    title: '运行线路',
    content: 0,
  },
  {
    title: '路线总数',
    content: 0,
  },
  {
    title: '车辆总数',
    content: 0,
  },
  {
    title: '最活跃线路',
    content: '',
  },
  {
    title: '最热门站点',
    content: '',
  },
])

const handlePanelCopy = (row) => {
  const text = `${row.title}: ${row.content}`
  navigator.clipboard
    .writeText(text)
    .then(() => {
      ZyMessage.success('复制成功', 0.8)
    })
    .catch(() => {
      ZyMessage.error('复制失败', 1)
    })
}

onMounted(() => {
  try {
    ws = new WebSocket(import.meta.env.VITE_PANEL_WS_URL)
    ws.onmessage = (e) => {
      const data = JSON.parse(e.data)
      panelData.value = data.data
    }
  } catch (_) {
    console.error('init ws error')
  }
})

onUnmounted(() => {
  if (ws) ws.close()
})

window.onbeforeunload = () => {
  if (ws) ws.close()
}
</script>

<style scoped>
.gutter-example :deep(.ant-row > div) {
  background: transparent;
  border: 0;
}

.gutter-row {
  margin-bottom: 28px;
}
</style>
