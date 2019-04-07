
function mountedScrollTop(cls) {
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

function convertMoneyStr(money) {
  return money.toString().replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')
}

export {mountedScrollTop, convertMoneyStr}