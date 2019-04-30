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
      name: this.searchStore.searchText,
      page: this.page,
      size: this.pageSize
    }
  }

  @action
  pullProjects = ({name, page, size}) => {
    api.Project.byName(name, page, size)
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

class SearchStore {

  @observable searchText = ''

  

  constructor() {
    this.projectResult = new ProjectResultStore(this)
  }

  @action
  setSearchText = (text) => {
    this.searchText = text
  }

}

export default new SearchStore()