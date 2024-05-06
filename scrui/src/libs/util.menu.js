import { useRouter } from 'vue-router'

/**
 * 根据权限列表筛选异步路由配置
 * @param {Array} routes - 路由配置表
 * @returns {Array} - 筛选后的路由配置
 */
function filterAsyncRouter(routes) {
  const res = []

  routes.forEach((route) => {
    // 创建临时变量 tmp  可以在后续的操作中不会修改原始的路由对象。
    const tmp = { ...route }
    if (!tmp.hidden && tmp.children) {
      // 先对子路由进行深度筛选，确保子路由也符合权限要求
      tmp.children = filterAsyncRouter(tmp.children)
      if (tmp.children && tmp.children.length > 0) {
        res.push(tmp)
      }
    } else {
      // 对于没有子路由的路由对象，直接进行权限判断
      if (!tmp.hidden) {
        res.push(tmp)
      }
    }
  })

  return res
}

export const menuList = function () {
  const asyncRoutes = useRouter().options.routes[0].children.filter((e) => !e.hidden)
  return filterAsyncRouter(asyncRoutes)
}
