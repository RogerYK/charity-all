import axios from 'axios'
import { getToken } from './auth';

const service = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 5000
})

service.interceptors.request.use(
  config => {
    const token = getToken()
    if (token) {
      config.headers['access_token'] = getToken()
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