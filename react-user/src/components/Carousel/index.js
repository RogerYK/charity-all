import React, { Component } from 'react'

import styles from './style.module.scss'


export default class Carousel extends Component {

  constructor(props) {
    super(props)
    this.state = {
      current: 0,
    }
    this.duration = 300
  }

  toLast() {
    this.setState({
      current:this.state.current-1,
    })
  }

  toNext() {
    this.setState({
      current: this.state.current+1
    })
  }

  leftAdjust() {
    setTimeout(()=> {
      this.duration = 0
      this.setState({current: this.props.children.length-1})
    }, 300)
  }

  rightAdjust() {
    setTimeout(()=> {
      this.duration = 0
      this.setState({current: 0})
    }, 300)
  }

  render() {
    const children = this.props.children
    const current = this.state.current
    const items = [...children.slice(-2), ...children, ...children.slice(0, 2)]
    const duration = this.duration
    if (this.duration === 0) this.duration = 300
    if (current < 0) this.leftAdjust();
    if (current >= children.length) this.rightAdjust();
    return (
      <div className={styles['carousel']}>
        <div className={styles['carousel-main']} style={{transform: `translateX(${-1500-current*750}px)`, transitionDuration: `${duration}ms`}}>
          {items.map((item, i) => (
            <div key={i} className={styles['carousel-item']}>{item}</div>
          ))}
        </div>
        <div className={styles['carousel-between-wrap']}>
          <div className={styles['carousel-between']}>
            <div onClick={()=>this.toLast()} className={styles['left']+' '+styles['img']}></div>
          </div>
          <div className={styles['carousel-between']}>
            <div onClick={()=>this.toNext()} className={styles['right']+' '+styles['img']}></div>
          </div>
        </div>
      </div>
    )
  }

}
