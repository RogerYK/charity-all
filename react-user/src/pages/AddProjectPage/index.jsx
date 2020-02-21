import React, { Component } from 'react'


import styles from './style.module.scss'
import AddProjectForm from './AddProjectForm';
import { observer, inject } from 'mobx-react';
import { message } from 'antd';

@inject('commonStore')
@observer
export default class AddProjectPage extends Component {

  componentDidMount = () => {
    const {commonStore, history} = this.props
    const {logined} = commonStore
    if (!logined) {
      message.info('未登录， 请先登录')
      setTimeout(() => {
        history.push('/login')
      }, 3000);
    }
  }

  render() {
    return (
      <div className={styles.container}>
        <div className={styles.header}>
          <span className={styles.title}>发布项目</span>
        </div>
        <div className={styles.content}>
          <AddProjectForm />
        </div>
      </div>
    ) 
  }

}