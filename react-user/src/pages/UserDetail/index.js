import React, { Component } from 'react'

import styles from './style.module.scss'
import api from '../../api';
import { inject } from 'mobx-react';


@inject('commonStore')
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

  handleFollow = (follow) => {
    const {commonStore, history} = this.props
    if (!commonStore.logined) {
      history.push('/login')
      return
    }
    const {id} = this.state.user;
    api.User.followUser({
      userId: id,
      follow,
    }).then(res => {
      return api.User.detail(id)
    }).then(res => {
      this.setState({user:res.data})
    })
  }

  render() {
    const {handleFollow} = this;
    const {user} = this.state;
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
                    <div>{user.favorUserCount} 关注</div>
                  </div>
                  <div>
                    <a
                      className={styles.bumo_address}
                      rel="noopener noreferrer"  
                      target="_blank" 
                      href={`https://explorer.bumotest.io/account/${user.bumoAddress}`}
                    >区块链地址>></a>
                  </div>
                </div>
              </div>
              <div className={styles['right']}>
                {user.followed? 
                  <div onClick={() => handleFollow(false)}  className={styles['btn']}>已关注</div>: 
                  <div onClick={() => handleFollow(true)} className={styles['btn']}>关注</div>
                }
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