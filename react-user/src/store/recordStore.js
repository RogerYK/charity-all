import { observable, reaction, action } from "mobx";
import api from '../api'
import userStore from "./userStore";

class RecordStore {

  @observable records = []

  @observable page = 0

  @observable total = 0

  pageSize = 5

  constructor() {
    
    reaction(
      () => this.page,
      this.pullRecords
    )
  }

  @action
  pullRecords = () => {
    api.Transaction.donation(userStore.currentUser.id,this.page, this.pageSize)
      .then(action(res => {
        this.records = res.data.content
        this.total = res.data.total
      }))
  }

  @action
  setPage = (page) => {
    this.page = page
  }

}

export default new RecordStore()