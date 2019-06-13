import { observable, action } from "mobx";
import api from "../api";

class IndexStore {
  @observable banners = observable.array();

  @observable recommendProjects = observable.array();

  @observable hotProjects = observable.array();

  @observable hotNews = observable.array();

  @observable latestNews = []

  @action
  pullData = () => {
    api.Banner.all().then(action(res => (this.banners = res.data)))
    api.Project.hot().then(action(res => (this.hotProjects = res.data)))
    api.News.hot().then(action(res => (this.hotNews = res.data)))
    api.Project.recommend().then(
      action(res => (this.recommendProjects = res.data))
    )
    api.News.latest().then(action(res => this.latestNews= res.data))
  };
}

export default new IndexStore()