import { observable, when, action, runInAction, reaction } from "mobx";
import api from "../api";


class DetailStore {

  @observable id

  @observable project

  constructor() {
    reaction(
      () => this.id,
      (id) => this.pullProject(id)
    )
  }

  @action
  async pullProject(id) {
    console.log(id)
    try {
      const res = await api.Project.detail(id)
      runInAction(() => {
        this.project = res.data
      })
    } catch (e) {
      console.log(e)
    }
  }

}

export default new DetailStore()