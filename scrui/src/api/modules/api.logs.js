import service from '../server'

export const apiLogList = (data) => {
  return service.post('/v1/api/log/list', data)
}
