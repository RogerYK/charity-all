import React, { Component } from 'react'
import { observable, action } from 'mobx';
import { observer } from 'mobx-react';
import styles from './style.module.scss'
import api from '../../api';
import { Avatar, Skeleton } from 'antd';

@observer
export default class NewsDetail extends Component {

  @observable news

  @observable pulling = true

  @observable success = false

  @observable msg

  constructor(props) {
    super(props)
    console.log(props)
    api.News.detail(props.match.params.id)
      .then(action(res => {
        this.news = res.data
        this.pulling = false
        this.success = true
      })).catch(res => {
        this.msg = res.msg
        this.pulling = false
      })
  }

  render() {
    const {
      news,
      pulling,
      success,
      msg
    } = this
    return (
      <div className={styles['main-wrap']}>
        <div className={styles['main']}>
        <Skeleton loading={pulling}>
        {success ?
          <>
          <div className={styles['title']}>{news.title}</div>
          <div className={styles['author']}>
            <img className={styles['avatar']} src={news.author.avatar} alt="avatar"/>
            <div className={styles['name']}>{news.author.nickName}</div>
            <div className={styles['time']}>发布于{news.createdTime}</div>
          </div>
          <div className={styles['news-content']} dangerouslySetInnerHTML={{__html: news.content}}></div>
          </>
          :
          <>
          <div className={styles['error-msg']}>{msg}</div>
          </>
        }
        </Skeleton>
        </div>
      </div>
    )
  }
}