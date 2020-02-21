import React, { Component } from 'react'
import {Link} from 'react-router-dom'

import styles from './style.module.scss'
import { Steps, Button } from 'antd';
import { inject, observer } from 'mobx-react';

@inject('userStore')
@observer
export default class Authentication extends Component {

  render() {
    const {currentUser} = this.props.userStore
    const {identifyStatus} = currentUser
    const currentMap = {
      'UnIdentify': 0,
      'Identifying': 1,
      'Identified': 2,
    }
    const current = currentMap[identifyStatus]
    console.log('current', current)
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
    return (
      <div className={styles['authentication']}>
        <div className="title">
          <div className={styles['desc']}>认证状态</div>
        </div>

        <div className={styles['content']}>
          <Steps 
            status="wait"
            current={current}>
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
          {current === 1 && <div>资料已提交，审核人员正在处理，请耐心等待</div>}
          {current === 2 && <div>认证已通过，可以发布项目</div>}
        </div>
      </div>
    )
  }
}