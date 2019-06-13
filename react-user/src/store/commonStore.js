import {observable, action, autorun, computed, get} from 'mobx'

class CommonStore {

  @observable accessToken = window.localStorage.getItem('token');

  @computed get logined() {
    return Boolean(this.accessToken)
  }


  constructor() {
    autorun(() => {
      if (this.logined) {
        window.localStorage.setItem('token', this.accessToken)
      }
    })
  }

  @action setToken(token) {
    console.log(token)
    this.accessToken = token
    console.log(this.accessToken)
  }

  @action
  removeToken() {
    this.accessToken = null
    window.localStorage.removeItem('token')
  }

}

export default new CommonStore();