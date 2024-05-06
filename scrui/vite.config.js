import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

export default defineConfig({
  plugins: [vue(), vueJsx()],
  server: {
    port: 5143,
    proxy: {
      '/v1': {
        target: 'https://em-bda.chat/',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/v1/, ''),
      },
    },
  },
  resolve: {
    // 别名
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      comps: fileURLToPath(new URL('src/components', import.meta.url)),
      views: fileURLToPath(new URL('src/views', import.meta.url)),
      store: fileURLToPath(new URL('src/store', import.meta.url)),
      utils: fileURLToPath(new URL('src/utils', import.meta.url)),
      libs: fileURLToPath(new URL('src/libs', import.meta.url)),
      api: fileURLToPath(new URL('src/api', import.meta.url)),
      styles: fileURLToPath(new URL('src/styles', import.meta.url)),
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: "@import '@/assets/styles/abstracts/_variables.scss';",
      },
      less: {
        modifyVars: {
          // 修改 Ant Design Vue 主题变量
          '@primary-color': '#d2b48c',
          '@success-color': '#80b178',
          '@warning-color': '#d8c49a',
          '@error-color': '#d1786b',
          '@heading-color': '#333333',
          '@text-color': '#666666',
          '@text-color-secondary': '#999999',
        },
        javascriptEnabled: true,
      },
    },
  },
})
