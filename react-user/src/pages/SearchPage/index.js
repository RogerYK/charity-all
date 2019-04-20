import React, { Component } from 'react'
import styles from './style.module.scss'
import SearchDiv from './searchDiv';
import { ProjectResult } from './ResultDiv';
import { observer, inject } from 'mobx-react';

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
            <ProjectResult />
          </div>
        </div>
      </div>
    )
  }
}