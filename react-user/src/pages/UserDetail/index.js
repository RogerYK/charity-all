import React, { Component } from 'react'

import styles from './style.module.scss'
import {Button} from 'antd'
import api from '../../api';


export default class UserDetail extends Component {

  state={
    user: {},
  }

  componentDidMount() {
    const id = this.props.match.params.id;
    api.User.detail(id)
      .then(res => {
        this.setState({user:res.data})
      })
  }

  render() {
    const {user} = this.state
    return (
      <div className="container-wrap">
        <div className={styles['container']}>
          <div className={styles['main-right']}>
            <div className={styles['header']}>
              <div className={styles['left']}>
                <img src={user.avatar} className={styles['avatar']} alt="avatar" />
                <div className={styles['info']}>
                  <div className={styles['name']}>{user.nickname}</div>
                  <div className={styles['follow']}>
                    <div>0 粉丝</div>
                    <div>{user.favorUserCount} 关注</div>
                  </div>
                </div>
              </div>
              <div className={styles['right']}>
                <div className={styles['btn']}>关注</div>
              </div>
            </div>
            <div className={styles['title']}>个人简介</div>
            <div className={styles['content']}>{user.presentation}</div>
            <div className={styles['title']}>其他信息</div>
            <div className={styles['other']}>
              <div className={styles['item']}>
                <div className={styles['num']}>{user.donatedCount}</div>
                <div>支持的项目</div>
              </div>
              <div className={styles['item']}>
                <div className={styles['num']}>0</div>
                <div>发布的动态</div>
              </div>
            </div>

          </div>
        </div>
      </div>
    )
  }
}