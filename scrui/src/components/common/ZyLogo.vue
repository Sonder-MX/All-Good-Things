<template>
  <div class="zy-logo">
    <img :width="size" :src="setting.websiteInfo.logo || url || logo" />
    <span v-if="showTitle" class="title">
      {{ setting.websiteInfo.name }}@{{ setting.websiteInfo.version || '1.0.0' }}
    </span>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import logo from '@/assets/logo.svg'
import setting from '@/setting.js'

const props = defineProps({
  url: {
    type: String,
    default: '',
  },
  showTitle: {
    type: Boolean,
    default: true,
  },
  size: {
    type: [String, Number],
    default: '56',
  },
})
// 定义可触发的事件
const emit = defineEmits(['logo-click'])
const clickLogo = () => {
  emit('logo-click')
}
</script>

<style lang="scss" scoped>
.zy-logo {
  display: flex;
  justify-content: left;
  align-items: center;
  .title {
    font-weight: bold;
    margin-left: 12px;
    font-size: 1.3rem;
    font-family: 'Baskerville Old Face';
  }
}
</style>
