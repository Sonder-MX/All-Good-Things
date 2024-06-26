<template>
  <teleport to="body">
    <transition name="modal" mode="out-in">
      <div v-if="show" class="modal" @click="handleMaskClick">
        <div class="modal-content-main" @click.stop>
          <div class="modal-content-head">
            <h5>{{ title }}</h5>
            <a-button type="danger" v-if="showClose" @click="closeModal">关闭</a-button>
          </div>
          <div
            class="modal-content"
            :style="{ minWidth: minWidth + 'px', minHeight: minHeight + 'px' }">
            <slot>
              <a-empty />
            </slot>
          </div>
        </div>
      </div>
    </transition>
  </teleport>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  show: {
    type: Boolean,
    default: false,
  },
  minWidth: {
    type: Number,
    default: 400,
  },
  minHeight: {
    type: Number,
    default: 300,
  },
  maxHeight: {
    type: Number,
    default: 800,
  },
  title: {
    type: String,
    default: '标题',
  },
  showClose: {
    type: Boolean,
    default: true,
  },
  maskClose: {
    type: Boolean,
    default: false,
  },
})
// 定义可触发的事件
const emit = defineEmits(['open', 'close'])

const openModal = () => {
  emit('open')
}
const handleMaskClick = () => {
  if (props.maskClose) {
    closeModal()
  }
}

const closeModal = () => {
  emit('close')
}
</script>

<style scoped lang="scss">
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9;
  overflow: hidden;
}

.modal-content-main {
  border-radius: 5px;
  box-sizing: border-box;
  height: 85%;
  overflow: hidden;
  .modal-content-head {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    border-bottom: 1px solid #e2e2e2;
    position: sticky;
    top: 0;
    left: 0;
    background-color: #fff;
    z-index: 10;
  }
  .modal-content {
    padding: 20px;
    max-height: calc(100% - 55px);
    box-sizing: border-box;
    overflow: auto;
    background-color: #fff;
  }
}

.modal-enter-active {
  transition: all 0.2s linear;
  animation: bounce-in 0.3s cubic-bezier(0.25, 0.46, 0.45, 0.94) both;
}

.modal-leave-active {
  transition: all 0.2s linear;
  animation: bounce-in 0.3s cubic-bezier(0.25, 0.46, 0.45, 0.94) both reverse;
}

@keyframes bounce-in {
  0% {
    transform: scale(0);
    opacity: 0;
  }
  50% {
    transform: scale(1.25);
    opacity: 0;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}
</style>
