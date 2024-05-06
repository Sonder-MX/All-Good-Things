const frameIn = [
  {
    path: '/',
    redirect: { name: 'index' },
    component: () => import('@/layout/index.vue'),
    children: [
      {
        path: '/index',
        name: 'index',
        meta: {
          cache: true,
          title: '首页',
          icon: 'shouye',
          requiresAuth: true,
        },
        component: () => import('@/views/home/index.vue'),
      },
      {
        path: '/dataCenter',
        name: 'dataCenter',
        meta: {
          cache: true,
          icon: 'shujuzhongxin',
          title: '数据大屏',
          requiresAuth: true,
        },
        component: () => import('@/views/bigscreen/index.vue'),
      },
      {
        path: '/sys',
        name: 'sys',
        meta: {
          title: '系统管理',
          icon: 'shezhi',
          requiresAuth: true,
        },
        children: [
          {
            path: '/dir-users-info',
            name: 'dir-users-info',
            meta: {
              title: '用户管理',
              icon: 'yonghuguanli',
              requiresAuth: true,
            },
            component: () => import('@/views/sys/users/dir-users-info.vue'),
          },
          {
            path: '/dir-users_opt_logs-info',
            name: 'dir-users_opt_logs-info',
            meta: {
              title: '接口日志',
              icon: 'rizhi',
              requiresAuth: true,
            },
            component: () => import('@/views/sys/optLogs/dir-users_opt_logs-info.vue'),
          },
        ],
      },
      {
        path: '/about',
        name: 'about',
        meta: {
          cache: true,
          title: '关于项目',
          icon: 'wendang',
          requiresAuth: true,
        },
        component: () => import('@/views/about/index.vue'),
      },
      {
        path: 'https://github.com/Sonder-MX',
        meta: {
          link: true,
          title: '我的Github',
          requiresAuth: true,
          icon: 'github',
        },
      },

      // 重定向页面 必须保留
      {
        path: '/redirect/:path(.*)/:_origin_params(.*)?',
        name: 'Redirect',
        hidden: true, //不展示在侧边栏菜单
        meta: {
          title: '重定向',
        },
        component: () => import('@/views/error/redirect'),
      },
    ],
  },
]

/**
 * 在主框架之外显示
 */
const frameOut = [
  // 登录
  {
    path: '/login',
    name: 'login',
    meta: {
      title: '登录',
    },
    component: () => import('@/views/sys/login/dir-login-info.vue'),
  },
  {
    path: '/dataCenter',
    name: 'dataCenter',
    meta: {
      title: '大屏展示',
    },
    component: () => import('@/views/bigscreen/index.vue'),
  },
]

/**
 * 错误页面
 */
const errorPage = [
  {
    path: '/401',
    name: '401',
    component: () => import('@/views/error/401.vue'),
    meta: {
      title: '401',
    },
  },
  {
    path: '/:pathMatch(.*)*',
    name: '404',
    component: () => import('@/views/error/404.vue'),
    meta: {
      title: '404',
    },
  },
]

// 导出需要显示菜单的
export const frameInRoutes = frameIn

// 重新组织后导出
export default [...frameIn, ...frameOut, ...errorPage]
