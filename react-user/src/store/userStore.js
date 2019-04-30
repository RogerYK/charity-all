import { observable, action, when } from "mobx";
import api from '../api'
import commonStore from './commonStore'

class UserStore {

  @observable currentUser;

  @observable pulling = true

  constructor() {
    when(
      () => commonStore.logined,
      () => this.pullUser()
    )
  }
  
  @action
  pullUser() {
    this.pulling = true;
    api.User.current()
      .then(action((res) => {
        this.currentUser = res.data
        this.pulling = false
      })).catch(action(res => {
        commonStore.setToken(null)
        this.pulling = false
        console.log('获取用户信息失败')
      }))
  }

  @action
  updateUser = (newUser) => {
    api.User.update(newUser)
      .then(action(res => {
        this.currentUser = newUser
      }))
  }

}

export default new UserStore()