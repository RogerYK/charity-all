import React from 'react'
import {convertMoneyStr} from '../../../../utils/format'

import styles from './style.module.scss'
import { Button } from 'antd';
import { Link } from 'react-router-dom';
import classNames from 'classnames'

const statusLable = {
  'UnPay': '未支付',
  'Paying': '支付中',
  'Success': '已完成',
  'Failed': '失败'
}

const Status = ({status}) => {
  const cls = classNames(styles.icon, styles[status])
  return (
    <div className={styles.transactionStatus}>
      <span className={cls}></span>
      <span className={styles.label}>{statusLable[status]}</span>
    </div>
  )
}

export default (props) => {

  const record = props.record
  const project = record.project
  const showLink = record.status === 'Success' || record.status === 'Failed'

  return (
    <div className={styles['record']}>
      <img className={styles['project-img']} src={project.img} alt="img"/>
      <div className={styles['project-info']}>
        <div className={styles['name']}>{project.name}</div>
        <div style={{display: 'flex', justifyContent: 'space-between', marginBottom: '10px'}}>
          <Status status={record.status} />
          {showLink ? <a rel="noopener noreferrer" target="_blank" href={`https://explorer.bumotest.io/tx/${record.hash}`} className={styles['other']}>查看交易</a> : null}
        </div>
        <div className={styles['money']}>
          <span className={styles['label']}>捐助金额</span>
          <span className={styles['num']}>￥{convertMoneyStr(record.money)}</span>
        </div>
      </div>
      <div className={styles['actions']}>
        <Link to={`/detail/${project.id}`}><Button style={{marginRight: '20px'}}>评价项目</Button></Link>
        <Link to={`/detail/${project.id}`}><Button type="primary">再次支持</Button></Link>
      </div>
    </div>
  )
}