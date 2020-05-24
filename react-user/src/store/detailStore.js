import { observable, when, action, runInAction, reaction } from "mobx";
import api from "../api";
import { message } from "antd";


class DetailStore {

  @observable id

  @observable project

  constructor() {
    reaction(
      () => this.id,
      (id) => {
        if (!this.project || this.project.id !== id) {
          this.project = null
          this.pullProject(id)
        }
      } 
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

  donate = (projectId, amount) => {
    api.Transaction.donate(projectId, amount)
      .then(res => {
        message.success('捐款成功')
        this.pullProject(this.id)
      }).catch(res => {
        message.error(res.msg)
      })
  }

}

export default new DetailStore()