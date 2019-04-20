import React, { Component } from 'react'

import styles from './style.module.scss'
import { Divider, Pagination } from 'antd';
import Record from './Record'

import { observer, inject } from 'mobx-react';

@inject('recordStore')
@observer
export default class DonationRecord extends Component {

  componentDidMount() {
    this.props.recordStore.pullRecords()
  }

  render() {
    const {total, records, setPage} = this.props.recordStore
    return (
      <div className={styles['donation-records']}>
        <div className={styles['header']}>
          <span className={styles['title']}>捐款记录</span>
          <span className={styles['total']}>共{total}个记录</span>
        </div>
        <Divider />
        <div className={styles['record-list']}>
          <div className={styles['content']}>
            {records.map((r, i) =><div key={i} style={{marginBottom: '20px', width: '640px'}}><Record key={i} record={r} /></div>)}
          </div>
            <div className={styles['pagination-wrap']}>
              <Pagination defaultCurrent={1} pageSize={9} total={total}
                onChange={(page) => setPage(page-1)}
               />
            </div>
        </div>
      </div>
    )
  }
}