import React, { Component } from 'react'
import styles from './style.module.scss'
import SearchDiv from './searchDiv';
import { ProjectResult, NewsResult } from './ResultDiv';
import { observer, inject } from 'mobx-react';
import { Tabs } from 'antd';
import UserResult from './ResultDiv/userResult';

const {TabPane} = Tabs

@inject('searchStore')
@observer
export default class SearchPage extends Component {

  render() {
    const total = this.props.searchStore.projectResult.total
    return (
      <div className={styles['search-container']}>
        <SearchDiv />
        <div className={styles['result']}>
          <div className={styles['title']}>
            搜索结果
            <span className={styles['total']}>共{total}个结果</span>
          </div>
          <div className={styles['content']}>
            <Tabs defaultActiveKey="1">
              <TabPane tab="项目" key="1">
                <ProjectResult />
              </TabPane>
              <TabPane tab="新闻" key="2">
                <NewsResult />
              </TabPane>
              <TabPane tab="用户" key="3">
                <UserResult />
              </TabPane>
            </Tabs>
          </div>
        </div>
      </div>
    )
  }
}