<template>
  <vue-particles id="tsparticles" :particlesLoaded="particlesLoaded" :options="particlesStyle" />

  <a-layout class="login-box">
    <a-layout-header class="login-logo">
      <ZyLogo class="login-img" />
    </a-layout-header>
    <a-layout-content class="login-content">
      <a-row align="middle" justify="center" class="login-content-main">
        <a-col class="login-content-bgc" :pull="1">
          <img :src="loginBg" width="480" />
          <h2>{{ setting.websiteInfo.desc }}</h2>
          <p style="margin-top: 15px">
            ✨✨✨ 欢迎使用 {{ setting.websiteInfo.name || 'EM·BDA' }}@{{
              setting.websiteInfo.version || '1.0.0'
            }}！
          </p>
        </a-col>

        <a-col class="login-content-form">
          <LoginForm />
          <RegisterForm />
        </a-col>
      </a-row>
    </a-layout-content>
    <a-layout-footer class="login-footer">
      <div v-if="setting.reference.show" class="copyright">
        {{ ` Copyright ©${new Date().getFullYear()} by`
        }}<a target="_blank" class="out-link" :href="setting.reference.authorizationUrl"
          >@{{ setting.reference.authorization }}</a
        >
        . All rights reserved. |
        <a target="_blank" class="out-link" href="https://beian.miit.gov.cn/#/Integrated/index">{{
          setting.reference.number
        }}</a>
      </div>
    </a-layout-footer>
  </a-layout>
</template>

<script setup>
import loginBg from '@/assets/img/login_bg.svg'
import LoginForm from './components/LoginForm.vue'
import RegisterForm from './components/RegisterForm.vue'
import ZyLogo from 'comps/common/ZyLogo.vue'
import setting from '@/setting.js'
import { particlesStyle } from '@/libs/particles.style.js'

const particlesLoaded = async (container) => {
  console.log('Particles container loaded', container)
}
</script>

<style lang="scss" scoped>
$color-bg: #ffffff;
.login-box {
  width: 100%;
  height: 100%;
  box-sizing: border-box;

  .login-logo,
  .login-footer {
    z-index: 100;
    color: #444343;
    background-color: #eeeeee5e;
  }

  .login-content {
    height: 100%;

    .login-content-main {
      height: 100%;

      .login-content-bgc {
        text-align: center;
        padding-bottom: 100px;
      }

      .login-content-form {
        border: 0.2rem solid #ddd;
        padding: 60px;
        border-radius: 8px;
        overflow: hidden;
        box-sizing: border-box;

        .login-title {
          margin-bottom: 30px;
        }

        .login-form {
          max-width: 400px;
          background-color: #fff;
          overflow: hidden;

          .login-form-button {
            margin-right: 15px;
          }
        }
      }
    }
  }

  .login-footer {
    text-align: center;
    font-size: 0.8rem;
    color: #828181;
  }
}
</style>
