import { observable, action, reaction, computed } from "mobx";
import api from "../api";

export class ProjectResultStore {

  @observable projects = observable.array()

  @observable total = 0

  @observable page = 0

  pageSize = 12

  constructor(searchStore) {
    this.searchStore = searchStore

    reaction(
      () => this.searchData,
      (data) => this.pullProjects(data)
    )
  }

  @computed
  get searchData() {
    return {
      keyword: this.searchStore.searchText,
      page: this.page,
      size: this.pageSize
    }
  }

  @action
  pullProjects = (form) => {
    api.Search.project(form)
      .then(res => {
        this.total = res.data.total
        this.projects = res.data.content
      })
  }

  @action
  setPage = (page) => {
    this.page = page
  }

}

class NewsResultStore {

  @observable newsList
  @observable page = 0
  @observable total = 0
  pageSize = 10

  constructor(searchStore) {
    this.searchStore = searchStore
    reaction(
      () => this.searchData,
      this.pullNews
    )
  }


  @computed
  get searchData() {
    return {
      keyword: this.searchStore.searchText,
      page: this.page,
      size: this.pageSize
    }
  }

  @action
  pullNews = (form) => {
    api.Search.news(form)
      .then(res => {
        this.total = res.data.total
        this.newsList = res.data.content
      })
  }

  @action
  setPage = (page) => {
    this.page = page
  }

}

class UserResultStore {

  @observable users
  @observable page = 0
  @observable total = 0
  pageSize = 10

  constructor(searchStore) {
    this.searchStore = searchStore
    reaction(
      () => this.searchData,
      this.pullUsers
    )
  }


  @computed
  get searchData() {
    return {
      keyword: this.searchStore.searchText,
      page: this.page,
      size: this.pageSize
    }
  }

  @action
  pullUsers = (form) => {
    api.Search.user(form)
      .then(res => {
        this.total = res.data.total
        this.users = res.data.content
      })
  }

  @action
  setPage = (page) => {
    this.page = page
  }
}


class SearchStore {

  @observable searchText = ''

  constructor() {
    this.projectResult = new ProjectResultStore(this)
    this.newsResult = new NewsResultStore(this)
    this.userResult = new UserResultStore(this)
  }

  @action
  setSearchText = (text) => {
    this.searchText = text
  }

}

export default new SearchStore()