

export function mountedScrollTop(cls) {
  if (cls.prototype.componentDidMount) {
    const old = cls.prototype.componentDidMount
    cls.prototype.componentDidMount = function() {
      window.scrollTo(0, 0)
      old.apply(this)
    }
  } else {
    cls.prototype.componentDidMount = function() {
      window.scrollTo(0, 0)
    }
  }
}