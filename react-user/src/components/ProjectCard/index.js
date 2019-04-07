import React from 'react'

import styles from './style.module.scss'
import Progress from '../Progress';


export default (props) => {

  const {img, name, author, raisedMoney, targetMoney, donorCount} = props.project

  const moneyStr = raisedMoney.toString().replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')
  const percent = (raisedMoney/targetMoney*100).toFixed(2);


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
          <div className={styles['money']}>
            ￥{moneyStr}
          </div>
          <div className={styles['percent']}>{percent}%</div>
        </div>
        <Progress value={percent}/>
        <div className={styles['count']}>{donorCount} 支持者</div>
      </div>
    </div>
  )
}