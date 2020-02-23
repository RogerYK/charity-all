import { observable, action, when } from "mobx";
import api from '../api'
import commonStore from './commonStore'

class UserStore {

  @observable currentUser = {};

  @observable pulling = true

  @observable pulled = false;

  constructor() {
    when(
      () => commonStore.logined,
      () => this.pullUser()
    )
  }
  
  @action
  pullUser(token) {
    if (!commonStore.accessToken) {
      return
    }

    this.pulling = true
    api.User.current()
      .then(action((res) => {
        this.currentUser = res.data
        this.pulled = true
        this.pulling = false
      })).catch(action(res => {
        commonStore.setToken(null)
        this.pulled = false
        this.pulling = false
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