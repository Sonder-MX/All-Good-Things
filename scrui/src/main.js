import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import Antd from 'ant-design-vue'
import { createFromIconfontCN } from '@ant-design/icons-vue'
import 'ant-design-vue/dist/antd.variable.min.css'
import '@/assets/styles/main.scss'
import Directives from './directives/index'
import Particles from '@tsparticles/vue3'
import { loadFull } from 'tsparticles'

// 创建 IconFont
const IconFont = createFromIconfontCN({
  scriptUrl: '//at.alicdn.com/t/c/font_4123035_17iywx1cj2r.js',
})

const app = createApp(App)

app
  .use(router)
  .use(Antd)
  .use(createPinia())
  .use(Directives)
  .use(Particles, {
    init: async (engine) => {
      await loadFull(engine)
    },
  })
  .component('IconFont', IconFont)
  .mount('#app')
