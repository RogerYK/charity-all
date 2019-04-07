import React from 'react'

import styles from './style.module.scss'

export default (props) => {
  const value = props.value;
  return (
    <div className={styles['progress']}>
      <div className={styles['mask']} style={{width: `${value < 100 ? 100-value : 0}%`}}></div>
    </div>
  )
}