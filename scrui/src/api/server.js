import axios from 'axios'
import dbUtils from '../libs/util.strotage'
import { ZyNotification } from '../libs/util.toast'
// 不能使用useRouter ,useRoute，他们需要在setup中调用执行后才能用
import router from '@/router'

// 创建 Axios 实例
const instance = axios.create({
  timeout: 10000,
})
// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    // 在请求发送前设置 token
    config.headers.authorization = dbUtils.get('token')
    return config
  },
  (error) => {
    return Promise.reject()
  }
)

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    // 在接收到响应数据前进行解析响应数据、错误处理等
    const contentType = response.headers['content-type']
    if (
      contentType === 'application/octet-stream' ||
      contentType === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    ) {
      // Blob类型文件下载需要请求头参数添加 responseType:'blob'  下载 导出等功能需要
      downloadFile(response)
    } else {
      // 响应数据是有效的 JSON 格式，继续处理
      return Promise.resolve(response.data)
    }
  },
  (error) => {
    // 统一处理错误
    return handleRequestError(error)
  }
)

// 下载blob二进制文件
const downloadFile = (response) => {
  const url = window.URL.createObjectURL(new Blob([response.data]))
  const filename = response.headers['x-filename']

  axios.get(url, { responseType: 'blob' }).then((res) => {
    const blob = new Blob([res.data])
    if (window.navigator.msSaveBlob) {
      // 兼容 IE，使用 msSaveBlob 方法进行下载
      window.navigator.msSaveBlob(blob, decodeURIComponent(filename))
    } else {
      // 创建一个 <a> 元素
      const link = document.createElement('a')
      link.href = window.URL.createObjectURL(blob)
      link.setAttribute('download', decodeURIComponent(filename))
      // 模拟点击下载
      link.click()
      // 清理 URL 和 <a> 元素
      link.remove()
      window.URL.revokeObjectURL(url)
    }
  })
}

// 统一处理错误
const handleRequestError = (error) => {
  // 进行错误处理
  if (error.response) {
    // 服务器响应错误
    let status = error.response.status
    switch (status) {
      case 400:
        ZyNotification.error(error.response.data.msg || '参数校验失败')
        return Promise.reject(error.response.data.msg ?? '参数校验失败')
      case 401:
        if (error.response.data.msg === '未登录，请先登录') {
          router.replace('/login')
          ZyNotification.error('账号已过期,请重新登录')
          return Promise.reject({ error: 'Unauthorized', msg: '账号已过期,请重新登录' })
        } else {
          ZyNotification.error(error.response.data.msg || '账号已过期,请重新登录')
          return Promise.reject({ error: '401', msg: error.response.data.msg })
        }
      case 404:
        ZyNotification.error(error.response.data.msg || '资源不存在')
        return Promise.reject({ error: '接口不存在', msg: error.response.data.msg })
      case 500:
        ZyNotification.error(error.response.data.msg || '服务器内部错误')
        return Promise.reject({ error: '服务器内部错误', msg: error.response.data.msg })
      default:
        ZyNotification.error('服务器响应错误，请稍后再试')
    }
  } else if (error.request) {
    // 请求未收到响应
    ZyNotification.error('请求未收到响应')
  } else {
    // 请求配置出错
    ZyNotification.error('请求配置出错')
  }
}

// 封装请求方法
class AxiosService {
  constructor() {
    if (AxiosService.instance) {
      return AxiosService.instance
    }
    AxiosService.instance = this
  }

  // GET 请求
  get(url, params = null) {
    return instance.request({
      method: 'get',
      url,
      params,
    })
  }

  // POST 请求
  post(url, data = null, params = null, responseType) {
    return instance.request({
      method: 'post',
      url,
      data,
      params,
      responseType,
    })
  }

  // PUT 请求
  put(url, data = null, params = null) {
    return instance.request({
      method: 'put',
      url,
      data,
      params,
    })
  }

  // DELETE 请求
  delete(url, params = null) {
    return instance.request({
      method: 'delete',
      url,
      params,
    })
  }
}

// 创建 AxiosService 实例
const axiosService = new AxiosService()

// 导出实例化后的 AxiosService 对象
export default axiosService
