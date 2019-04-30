import { observable, action } from "mobx";


class UiStore {

  @observable headerShow = true

  @observable scrollTop = false

  @observable scrolledY = 0

  constructor() {
    window.addEventListener('scroll', this.handScroll)
  }

  @action
  handScroll = (e) => {
    console.log(window.scrollY)
    this.headerShow = (window.scrollY <= this.scrolledY || window.scrollY <= 50) 
    this.scrolledY = window.scrollY
  }
}

export default new UiStore()