import React, { Component } from 'react'
import {convertMoneyStr} from '../../../utils/format'

import styles from './style.module.scss'
import Progress from '../../../components/Progress';

export default class RankShow extends Component {

  constructor(props) {
    super(props)
    this.state = {
      curProject: 0,
      curNews: 0,
    }
  }

  render() {
    const {curProject, curNews} = this.state
    const {hotProjects, hotNews} = this.props
    return (
      <div className={styles['rank-show']}>
        <div className={styles['left-rank']}>
          <div className={styles['title']}>热门项目</div>
          <div className={styles['line']}></div>
          <div className={styles['content']}>
            <div className={styles['left']}>
              {hotProjects.map((p, i) => (
                <div key={i} className={`${styles['item']} ${i===curProject ? styles['active']:''}`} onMouseEnter={() => {
                  this.setState({ curProject: i })
                }}>{i+1}</div>
              ))}
            </div>
            <div className={styles['project-wrap']}>
            {hotProjects.map((p, i) => (
                <div key={i} className={`${styles['project']} ${i===curProject?styles['active']:''}`}>
                  <img src={p.img} alt="img"/>
                  <div className={styles['body']}>
                    <div className={styles['name']}>{p.name}</div>
                    <div className={styles['author']}>
                      <img src={p.author.avatar} alt="avatar"/>
                      <span>{p.author.nickName}</span>
                    </div>
                    <div className={styles['summary']}>{p.summary}</div>
                    <div className={styles['raise-info']}>
                      <div className={styles['money']}>￥{convertMoneyStr(p.raisedMoney)}</div>
                      <div className={styles['percent']}>{(p.raisedMoney/p.targetMoney*100).toFixed(2)}%</div>
                    </div>
                    <Progress value={p.raisedMoney/p.targetMoney*100} />
                    <div className={styles["people"]}>{p.donorCount} 支持者</div>
                  </div>
                </div>
            ))}
            </div>
          </div>
        </div>
        <div className={styles['right-rank']}>
          <div className={styles['title']}>热门新闻</div>
          <div className={styles['content']}>
            {hotNews.map((n, i) => (
              <div key={n.id} className={`${styles['activity']} ${i===curNews? styles['active']:''}`}
                onMouseEnter={() => {
                  this.setState({curNews:i})
                }}
              >
                <div className={styles['info']}>
                  <div className={styles['title']}>{n.name}</div>
                </div>
                <img src={n.img} className={styles['ico']} alt="ico"/>
              </div>
            ))}
          </div>
        </div>
      </div>
    )
  }
}

