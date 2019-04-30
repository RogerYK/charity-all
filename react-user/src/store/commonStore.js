import {observable, action, autorun, computed} from 'mobx'

class CommonStore {

  @observable accessToken = window.localStorage.getItem('token');

  @computed get logined() {
    return Boolean(this.accessToken)
  }


  constructor() {
    autorun(() => {
      if (this.accessToken) {
        window.localStorage.setItem('token', this.accessToken)
      }
    })
  }

  @action setToken(token) {
    this.accessToken = token
  }

  @action
  removeToken() {
    window.localStorage.removeItem('token')
  }

}

export default new CommonStore();