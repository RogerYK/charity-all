import axios from 'axios'
import commonStore from '../store/commonStore'

const service = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 5000
})

service.interceptors.request.use(
  config => {
    if (commonStore.accessToken) {
      config.headers['access_token'] = commonStore.accessToken
    }
    return config
  },
  error => {
    console.log(error)
    Promise.reject(error)
  }
)

service.interceptors.response.use(
  response => {
    const data = response.data
    if (data.errCode !== 0) {
      return Promise.reject(data)
    }
    return data
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)

export default service