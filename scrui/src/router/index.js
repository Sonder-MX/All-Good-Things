import { createRouter, createWebHashHistory } from 'vue-router'
import routes from './routes'
import dbUtils from '../libs/util.strotage.js'
import { useSearchStore } from '@/stores/search.js'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  },
  routes,
})

router.beforeEach(async (to, from, next) => {
  const searchStore = useSearchStore()
  searchStore.showSearchPanel(false)
  // 检查是否需要登录
  if (to.meta.requiresAuth) {
    const isLoggedIn = dbUtils.get('token')
    if (isLoggedIn) {
      // 已经登录 访问登录页则直接跳到主页
      if (to.name === 'login') {
        return next('/')
      }
      return next()
    }
    dbUtils.clear()
    // 重定向到登录页
    return next({ name: 'login' })
  }
  // 无需登录的页面 直接访问即可
  next()
})

router.afterEach((to, from) => {
  window.document.title = to.meta.title + ' - EM·BDA'
})

router.onError((error) => {
  // 在这里处理路由错误
  console.error('路由错误:', error)
})

export default router
