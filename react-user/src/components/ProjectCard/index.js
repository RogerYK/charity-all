import React from 'react'

import styles from './style.module.scss'
import Progress from '../Progress';



function getStatusTitle(status, raisedMoney) {
  if (status === 0) {
    return '创建中'
  } else if (status === 1) {
    return '申请中'
  } else {
    return raisedMoney.toString().replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')
  } 
}


export default (props) => {

  const {img, name, author, raisedMoney, targetMoney, donorCount, status} = props.project

  const percent = (raisedMoney/targetMoney*100).toFixed(2);
  const statusTitle = getStatusTitle(status, raisedMoney)

  return (
    <div className={`${styles['project-card']} ${props.bordered ? styles['bordered']: ''}`}>
      <img className={styles['cover']} src={img} alt="logo"/>
      <div className={styles['body']}>
        <div className={styles['project-name']}>{name}</div>
        <div className={styles['author']}>
          <img className={styles['logo']} src={author.avatar} alt="logo" />
          <div className={styles['name']}>{author.nickName}</div>
        </div>
        <div className={styles['status']}>
          <div className={styles['title']}>
            ￥{statusTitle}
          </div>
          {status > 2 && <div className={styles['right']}>{percent}%</div>}
        </div>
        <Progress value={percent}/>
        <div className={styles['count']}>{donorCount} 支持者</div>
      </div>
    </div>
  )
}