import { observable, flow, action, autorun, reaction } from "mobx";
import api from '../api'

class ExploreStore {

  @observable page = 1

  @observable total = 0

  @observable projects = []

  @observable categories = []

  @observable curCategoryId = null

  @observable direction = 'desc'

  @observable field = 'createdTime'

  pageSize = 12

  constructor() {
    reaction(
      () => ({curCategoryId: this.curCategoryId, 
        page: this.page,
        size: this.pageSize,
        direction: this.direction,
        field: this.field
      }),
      () => this.pullProjects()
    )
  }

  pullCategories = flow(function*() {
    const res = yield api.Category.all()
    this.categories = res.data
    if (this.categories.length > 0) {
      this.curCategoryId = this.categories[0].id
    }
    console.log(this.curCategoryId)
  })

  pullProjects = flow(function* () {
    const res = yield api.Project.byCategory(this.curCategoryId,
      this.page, this.pageSize, this.direction, this.field)
    this.total = res.data.total
    this.projects = res.data.content
  })

  @action
  setPage = (page) => {
    this.page = page
  }

  @action
  setCurCategoryId = (id) => {
    this.curCategoryId = id
  }

  @action
  setDirection = (direction) => {
    this.direction = direction
  }

  @action
  setField = (field) => {
    this.field = field
  }
}

export default new ExploreStore()