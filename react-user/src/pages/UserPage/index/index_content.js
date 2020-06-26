import React, { Component } from 'react'
import { Card, Avatar, Divider } from 'antd';

import styles from './index_content.module.scss'
import { observer, inject } from 'mobx-react';
import defaultAvatar from './charity.png'

@inject('userStore')
@observer
export default class IndexContent extends Component {

  render() {
    const user = this.props.userStore.currentUser
    return (
      <div className={styles['user-content']}>
        <Card
          className={styles['user-info']}
          bordered={false}
          hoverable={false}
        >
          <div className={styles['user-account']}>
            <Avatar size={64} src={user.avatar ? user.avatar : defaultAvatar} />
            <div className={styles['name']}>{user.nickname}</div>
            <div className={styles['brief']}>{user.presentation ? user.presentation: '还没有个人介绍，用一句话来介绍你自己吧'}</div>
          </div>

          <div className={styles['user-items']}>
            <div className={styles['item']}>
              <span className={styles['label']}>职业</span>
              <span className={styles['field']}>{user.profession? user.profession : '未知'}</span>
            </div>
            <div className={styles['item']}>
              <span className={styles['label']}>位置</span>
              <span className={styles['field']}>{user.address? user.address.split(':').join(' ') : '未知'}</span>
            </div>
          </div>
          <div gutter={8} className={styles['footer']}>
            <div className={styles['footer-item']} span={6}>发起项目 {user.releasedProjectCount}</div>
            <div className={styles['footer-item']} span={6}>关注者 {user.favorUserCount}</div>
            <div className={styles['footer-item']} span={6}>捐助项目 {user.donatedCount}</div>
            <div className={styles['footer-item']} span={6}>捐助金额 {user.donatedMoney}</div>
          </div>
        </Card>
        <Card
          bordered={false}>
          <div className={styles['message-title']}>项目动态</div>
          <Divider />
          <div className={styles['message-list']}>
            <div className={styles['empty']}>暂时没有动态</div>
          </div>
          <div className={styles['message-title']}>关注者动态</div>
          <Divider />
          <div className={styles['message-list']}>
            <div className={styles['empyt']}>暂时没有动态</div>
          </div>
        </Card>
      </div>
    )
  }
}