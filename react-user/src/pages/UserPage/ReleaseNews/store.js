import {observable , action, autorun } from "mobx";
import api from "../../../api";
import { message } from "antd";

export default class ReleaseNewsStore {
  
  @observable news = []

  @observable page = 1

  @observable total = 0

  pageSize = 9

  constructor(userId) {
    autorun(() => {
      this.pullNews([userId, this.page, this.pageSize])
    })
  }

  @action
  setPage = (page) => this.page = page

  @action
  pullNews = (data) => {
    api.News.byUser(...data)
      .then(action(res => {
        this.news = res.data.content
        this.total = res.data.total
      })).catch(res => {
        message.error(res.msg)
      })
  }
}