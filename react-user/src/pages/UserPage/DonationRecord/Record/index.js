import React from 'react'
import {convertMoneyStr} from '../../../../utils/format'

import styles from './style.module.scss'
import { Button } from 'antd';

export default (props) => {

  const record = props.record

  const project = record.project

  return (
    <div className={styles['record']}>
      <img className={styles['project-img']} src={project.img} alt="img"/>
      <div className={styles['project-info']}>
        <div className={styles['name']}>{project.name}</div>
        <div className={styles['other']}></div>
        <div className={styles['money']}>
          <span className={styles['label']}>捐助金额</span>
          <span className={styles['num']}>￥{convertMoneyStr(record.money)}</span>
        </div>
      </div>
      <div className={styles['actions']}>
        <Button style={{marginRight: '20px'}}>评价项目</Button>
        <Button type="primary">再次支持</Button>
      </div>
    </div>
  )
}