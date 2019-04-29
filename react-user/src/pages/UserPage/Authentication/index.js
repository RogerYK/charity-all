import React, { Component } from 'react'
import {Link} from 'react-router-dom'

import styles from './style.module.scss'
import { Steps, Divider, Button } from 'antd';

export default class Authentication extends Component {

  constructor(props) {
    super(props)
    this.state = {
      current: 0,
    }
  }

  render() {
    const steps = [{
      title: '上传资料',
      info: '请完整的填写资料，并提交。'
    }, {
      title: '等待审核',
      info: '正在审核，请等待，大约2~3天'
    }, {
      title: '认证通过',
      info: '认证已通过，可以区发布自己的项目'
    }]
    const current = this.state.current
    return (
      <div className={styles['authentication']}>
        <div className={styles['header']}>
          <div className={styles['title']}>认证状态</div>
        </div>
        <Divider />

        <div className={styles['content']}>
          <Steps 
            status="wait"
          current={this.state.current}>
            {steps.map(s => (
              <Steps.Step key={s.title} title={s.title}></Steps.Step>
            ))}
          </Steps>
          <div className={styles['info']}>{steps[current].info}</div>
        </div>

        <div className={styles['resort-link-wrap']}>
          {current === 0 ? <Link to="/resort" className={styles['resort-link']}>
          <Button type="primary">填写资料</Button>
          </Link> : null}
        </div>
      </div>
    )
  }
}