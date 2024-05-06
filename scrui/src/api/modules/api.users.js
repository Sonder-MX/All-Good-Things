import service from '../server'

export const usersList = (data) => {
  return service.post('/v1/api/user/list', data)
}

export const myselfInfo = () => {
  return service.get('/v1/api/user/info')
}
