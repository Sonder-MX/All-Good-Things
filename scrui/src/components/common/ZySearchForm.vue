<template>
  <a-row class="zy-search-form">
    <a-form
      class="search-form"
      name="keyword"
      :model="formValue"
      :rule="rules"
      layout="inline"
      @finish="handleFinish">
      <slot></slot>
      <a-form-item>
        <a-space>
          <a-button type="primary" html-type="submit">查询</a-button>
          <slot name="btn"></slot>
          <a-button @click="resetForm">重置</a-button>
        </a-space>
      </a-form-item>
    </a-form>
  </a-row>
</template>

<script setup>
defineProps({
  formValue: {
    type: Object,
  },
  rules: {
    type: Object,
    default: () => ({}),
  },
})
const emit = defineEmits(['reset', 'submit'])

const handleFinish = () => {
  emit('submit')
}

const resetForm = () => {
  emit('reset')
}
</script>

<style lang="scss" scoped>
.zy-search-form {
  box-sizing: border-box;
  width: 100%;
  border-bottom: 1px solid #eeeeee;
  margin-bottom: 8px;
  padding-bottom: 10px;

  .search-form {
    background-color: #fff;
  }
}
</style>
