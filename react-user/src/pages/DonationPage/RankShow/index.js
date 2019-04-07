import React, { Component } from 'react'
import {convertMoneyStr} from '../../../utils'

import styles from './style.module.scss'
import Progress from '../../../components/Progress';
import { getHotProjects, getHotActivities } from '../../../api';

export default class RankShow extends Component {

  constructor(props) {
    super(props)
    this.state = {
      projects: [],
      activities: [],
      curProject: 0,
      curActivity: 0,
    }
  }

  componentDidMount() {
    getHotProjects()
      .then(res => {
        if (res.errCode === 0) {
          this.setState({projects: res.data})
        }
      })
    getHotActivities()
      .then(res => {
        if (res.errCode === 0) {
          this.setState({activities: res.data})
        }
      })
  }


  render() {
    const curProject = this.state.curProject
    const curActivity = this.state.curActivity
    return (
      <div className={styles['rank-show']}>
        <div className={styles['left-rank']}>
          <div className={styles['title']}>热门项目</div>
          <div className={styles['line']}></div>
          <div className={styles['content']}>
            <div className={styles['left']}>
              {this.state.projects.map((p, i) => (
                <div key={i} className={`${styles['item']} ${i===curProject ? styles['active']:''}`} onMouseEnter={() => {
                  this.setState({ curProject: i })
                }}>{i+1}</div>
              ))}
            </div>
            <div className={styles['project-wrap']}>
            {this.state.projects.map((p, i) => (
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
          <div className={styles['title']}>公益活动</div>
          <div className={styles['content']}>
            {this.state.activities.map((a, i) => (
              <div key={i} className={`${styles['activity']} ${i===curActivity? styles['active']:''}`}
                onMouseEnter={() => {
                  this.setState({curActivity:i})
                }}
              >
                <div className={styles['info']}>
                  <div className={styles['title']}>{a.name}</div>
                </div>
                <img src={a.img} className={styles['ico']} alt="ico"/>
              </div>
            ))}
          </div>
        </div>
      </div>
    )
  }
}

