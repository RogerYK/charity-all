import { observable, reaction, action } from "mobx";
import api from '../api'
import userStore from './userStore'

class FavoriteStore {

  @observable projects = []

  @observable page = 0

  @observable total = 0

  pageSize = 5

  constructor() {
    
    reaction(
      () => this.page,
      this.pullProjects
    )
  }

  @action
  pullProjects = () => {
    api.Project.favoritedBy(userStore.currentUser.id)
      .then(action(res => {
        this.projects = res.content
        this.total = res.total
      }))
  }

  @action
  setPage = (page) => {
    this.page = page
  }

}

export default new FavoriteStore()