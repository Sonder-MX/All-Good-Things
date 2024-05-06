import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import { useDbStore } from './db.js'
import dbUtils from '../libs/util.strotage.js'
import { useTabsStore } from './tabs'
import { authLogin } from '../api/modules/api.auth'
import { ZyNotification, ZyMessage } from '../libs/util.toast'
import { myselfInfo } from '../api/modules/api.users'

export const useAuthStore = defineStore('auth', () => {
  let router = useRouter()
  const db = useDbStore()
  // 重置tabs
  let tabStore = useTabsStore()

  async function logout() {
    // 执行退出登录逻辑，清除用户凭证和重置用户状态,重置tabs
    tabStore.resetTabs()
    // 清除所有缓存
    db.removeAllInfo()
    // 导航到登录页或其他适当的页面
    await router.replace('/login')
  }

  async function login(value) {
    return new Promise((resolve, reject) => {
      authLogin(value)
        .then((res) => {
          if (res.code !== 200) {
            ZyMessage.error(res.msg)
            return reject(res)
          }
          dbUtils.clear()
          dbUtils.set('token', res.data.token)

          myselfInfo().then(({ data }) => {
            // 存储用户信息
            dbUtils.set('userInfo', data)
            resolve(data)
            ZyNotification.success(`欢迎: ${data.username}`, '登录成功')
            router.replace('/')
          })
        })
        .catch((err) => {
          reject(err)
        })
    })
  }

  return { logout, login }
})
