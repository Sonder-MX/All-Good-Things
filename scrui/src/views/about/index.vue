<template>
  <section class="zy-container">
    <div class="img-box">
      <ZyLogo :showTitle="false" size="800" />
    </div>
    <hr />
    <section class="one">
      <header class="major">
        <h1 style="font-weight: 500">EM · BDA</h1>
        <p class="sub-title">
          基于
          <a href="https://cn.vuejs.org/" target="_blank">Vue3</a> +
          <a href="https://pinia.vuejs.org/zh/" target="_blank">Pinia</a> +
          <a href="https://ant-design.antgroup.com/index-cn" target="_blank">Ant Design Vue</a> +
          <a href="https://vitejs.dev/" target="_blank">Vite</a>.
        </p>
        <p>
          EM·BDA是一个现代化的数据大屏系统，提供了一系列功能丰富的面板和图表，帮助管理人员快速分析校车运营效率的应用
        </p>

        <a href="https://github.com/Sonder-MX" target="_blank">
          <a-button type="primary">Github主页</a-button>
        </a>
        <a href="https://xcyy.lzxk.edu.cn/admin/login/?next=/admin/" target="_blank">
          <a-button type="primary">后台管理</a-button>
        </a>
      </header>
      <div class="zy-code">
        <code> $ https://github.com/Sonder-MX </code>
      </div>
    </section>
    <section class="intro">
      <a-row justify="center" align="top" :gutter="16">
        <a-col>
          <a-card :bordered="true">
            <p>✨Tips: 本项目暂未开源</p>
            <p>✨作者邮箱：sonder-mx@outlook.com</p>
          </a-card>
        </a-col>
      </a-row>
      <ZySectionHeader title="其他开源项目" titleNum="01" />
      <ZyWorksSection :dataList="worksList" />
      <ZySectionHeader title="支持" titleNum="02" />
      <div class="zs-list">
        <img class="zs" :src="img_7" />
        <img class="zs" :src="img_8" />
      </div>
    </section>
  </section>
</template>

<script setup>
import { reactive } from 'vue'
import ZyLogo from '../../components/common/ZyLogo.vue'
import ZyWorksSection from '../../components/common/ZyWorksSection.vue'
import ZySectionHeader from '../../components/common/ZySectionHeader.vue'
import img_7 from '../../assets/img/img_7.png'
import img_8 from '../../assets/img/img_8.png'

const worksList = reactive([
  {
    _id: '64eec6a1a0d58a3cda8ce1ce',
    title: '猿星球',
    abstract:
      '👍个人主页、个人博客基于Django、Django REST framework、Vue3、Quasar、Vite、Python搭建集成网站和开发管理后台应用。前后端分离完整系统。',
    sourceUrl: 'https://github.com/Sonder-MX/PlanetApe',
    framework: 'Django、DRF、Vue3、Quasar、Vite、PostgreSQL',
    isDone: true,
  },
  {
    _id: '64d2f6bf97ad53aea60d4f4f',
    title: '苍穹外卖',
    abstract: '本项目是使用 Spring Boot 框架开发的一个在线外卖订购系统',
    sourceUrl: 'https://github.com/Sonder-MX/sky-take-out',
    framework: 'SprintBoot3、MyBatis、Mysql、Redis',
    isDone: true,
  },
  {
    _id: '64d2f64d97ad53aea60d4f43',
    title: 'mx-pwd',
    abstract: '使用 Tauri + Sqlite 和 Vue3 + TS 开发密码管理器 ',
    sourceUrl: 'https://github.com/Sonder-MX/mx-pwd',
    framework: 'Tauri、TypeScript、Vue3、Quasar、Vite',
    isDone: false,
  },
  {
    _id: '64d2f5aa97ad53aea60d4f35',
    title: '个人博客 Blogs',
    abstract:
      '👀👀简单小型的全栈开发个人博客系统,前台展示和后台管理一体化。vue3 + django + sqlite 包含前端博客展示、后台管理。整套博客系统开箱即用',
    sourceUrl: 'https://github.com/Sonder-MX/Blogs-Django-Vue',
    framework: 'Vue3、Django',
    isDone: true,
  },
  {
    _id: '64d2f55097ad53aea60d4f29',
    title: '后台管理模板',
    abstract: '使用Vue3 + TypeScript + Vite开发后台管理模板',
    sourceUrl: 'https://github.com/Sonder-MX/vue-admin-template',
    framework: 'TypeScript、Vue、Vite、Pinia',
    isDone: true,
  },
  {
    _id: '64d2f43a97ad53aea60d4ef1',
    title: 'SpringBoot登录组件',
    abstract: '采用SpringBoot3 + Vue3编写的前后端分离模版项目，集成多种技术栈，使用JWT校验方案',
    sourceUrl: 'https://github.com/Sonder-MX/springboot-vue-login',
    framework: 'Mybatis-Plus、RabbitMQ、SpringSecurity、Swagger、JWT、Vue3、Element-Plus',
    isDone: true,
  },
])
</script>

<style lang="scss" scoped>
.img-box {
  width: 120px;
  height: auto;
  margin: 2rem auto 0;
}

.intro {
  max-width: 1200px;
  margin: 0 auto;

  .social-list {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 2.5rem 0;

    .iconfont {
      font-size: 2rem;
      margin: 5px;
    }
  }

  .zs-list {
    display: flex;

    .zs {
      display: inline-block;
      width: 120px;
      margin: 1rem;
    }
  }
}

.one {
  max-width: 1200px;
  margin: 2rem auto;

  .major {
    text-align: center;
    margin-bottom: 2rem;

    h1 {
      margin-bottom: 1rem;
    }

    p:nth-child(3) {
      margin-bottom: 1rem;
    }

    a {
      margin: 0.5rem;
    }
  }
}
</style>
