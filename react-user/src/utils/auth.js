import Cookies from 'js-cookie'

const tokenKey = 'access_token'

export function getToken() {
  return Cookies.get(tokenKey)
}

export function setToken(token) {
  return Cookies.set(tokenKey, token)
}

export function remove(token) {
  return Cookies.remove(token)
}