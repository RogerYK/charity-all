import { observable, action } from "mobx";
import api from "../api";
import userStore from './userStore'

class ReleaseStore {

  @observable projects = []

  @observable total = 0

  @observable page = 0


  @action
  pullProjects = () => {
    api.Project.releasedBy(userStore.currentUser.id)
      .then(action(res => {
        this.projects = res.data.content
        this.total = res.data.total
      }))
  }

  @action
  setPage = (page) => {
    this.page = page
  }

  @action
  saveProject = (form) => {
    return api.Project.save(form)
      .then(action(res => {
        this.pullProjects()
        return res
      }))
  }
}

export default new ReleaseStore()