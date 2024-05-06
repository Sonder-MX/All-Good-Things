<template>
  <div v-show="getShow">
    <h2 class="register-title">注册</h2>
    <a-form
      loading
      :model="state.registerState"
      name="normal_register"
      class="register-form"
      @finish="onFinish"
      @finishFailed="onFinishFailed">
      <a-form-item name="username">
        <a-input
          v-model:value="state.registerState.username"
          allowClear
          autocomplete="off"
          size="large"
          placeholder="用户名">
          <template #prefix>
            <IconFont type="icon-yonghuming" style="font-size: 18px" />
          </template>
        </a-input>
      </a-form-item>
      <a-form-item name="email" :rules="[{ required: true, message: '请输入邮箱!' }]">
        <a-input
          v-model:value="state.registerState.email"
          allowClear
          autocomplete="off"
          size="large"
          placeholder="邮箱">
          <template #prefix>
            <IconFont type="icon-yonghuming" style="font-size: 18px" />
          </template>
        </a-input>
      </a-form-item>
      <a-form-item name="password" :rules="[{ required: true, message: '请输入密码!' }]">
        <a-input-password
          v-model:value="state.registerState.password"
          allowClear
          autocomplete="off"
          placeholder="密码"
          size="large">
          <template #prefix>
            <IconFont type="icon-mima" style="font-size: 18px" />
          </template>
        </a-input-password>
      </a-form-item>

      <a-form-item>
        <a-button
          type="primary"
          style="width: 100%"
          :disabled="disabled"
          html-type="submit"
          size="large"
          class="register-form-button">
          注册
        </a-button>
      </a-form-item>
      <a-form-item>
        <a-button
          @click="handleBackLogin"
          style="width: 100%"
          size="large"
          class="register-form-button">
          返回登录
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup>
import { reactive, computed } from 'vue'
import { useLoginState } from '@/stores/useLogin.js'
import { authRegister } from '../../../../api/modules/api.auth'
import { ZyNotification } from '../../../../libs/util.toast'

const { handleBackLogin, getLoginState } = useLoginState()
const getShow = computed(() => {
  return getLoginState.value === 'register'
})

const state = reactive({
  registerState: {
    username: '',
    email: '',
    password: '',
  },
})

const onFinish = (values) => {
  authRegister(values).then((res) => {
    if (res.code === 200) {
      ZyNotification.success('用户注册成功')
      handleBackLogin()
    } else {
      ZyNotification.error(res.msg)
    }
  })
}
const onFinishFailed = (errorInfo) => {
  console.log('Failed:', errorInfo)
}
const disabled = computed(() => {
  return !(state.registerState.email && state.registerState.password)
})
</script>

<style lang="scss" scoped>
.register-title {
  margin-bottom: 30px;
}

.register-form {
  max-width: 400px;
  background-color: #fff;
  overflow: hidden;

  .register-form-button {
    margin-right: 15px;
  }
}
</style>
