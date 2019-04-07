import React, { Component } from 'react'

import styles from './style.module.scss'
import { Divider, Pagination } from 'antd';
import Record from './Record'

import {getRecordInfo, getRecords} from '@/api'

export default class DonationRecord extends Component {

  constructor(props) {
    super(props)
    this.state = {
      total: 0,
      records: [],
      page: 1,
    }
  }

  componentDidMount() {
    getRecords(0).then(res => {
      this.setState({
        page: 1,
        total: res.data.total,
        records: res.data.content,
      })
    })
  }


  render() {
    const total = this.state.total
    const records = this.state.records
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
              <Pagination defaultCurrent={1} current={this.state.page} pageSize={9} total={this.state.total} />
            </div>
        </div>
      </div>
    )
  }
}