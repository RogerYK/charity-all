import React, { Component } from 'react'

import styles from './style.module.scss'
import { Divider, Pagination, Empty, Button } from 'antd';
import Record from './Record'

import { observer, inject } from 'mobx-react';
import RechargeModal from './RechargeModal';
import { observable, action } from 'mobx';

@inject('recordStore', 'userStore')
@observer
export default class DonationRecord extends Component {

  @observable modalVisible = false

  @action
  showModal = () => {
    this.modalVisible = true
  }

  @action
  hideModal = () => {
    this.modalVisible = false
  }

  componentDidMount() {
    this.props.recordStore.pullRecords()
  }

  render() {
    const {modalVisible, showModal, hideModal} = this
    const {total, records, setPage} = this.props.recordStore
    const {currentUser} = this.props.userStore
    return (
      <div className={styles['donation-records']}>
        <div className={styles['header']}>
          <div>
            <span className={styles['title']}>捐款记录</span>
            <span className={styles['total']}>共{total}个记录</span>
          </div>
          <div>
            <span className={styles.money}>余额：{currentUser.remainMoney}元</span>
            <Button onClick={showModal}>充值</Button>
          </div>
        </div>
        <Divider />
        <div className={styles['record-list']}>
          <div className={styles['content']}>
            {total > 0 ?
              records.map((r, i) =>
                <div key={i} style={{marginBottom: '20px', width: '640px'}}>
                  <Record key={i} record={r} />
                </div>)
              :
              <Empty description={<span>没有记录</span>} />
            }
          </div>
          <div className={styles['pagination-wrap']}>
            <Pagination defaultCurrent={1} pageSize={9} total={total}
              onChange={(page) => setPage(page)}
            />
          </div>
        </div>

        <RechargeModal visible={modalVisible} onCancel={hideModal}  />
      </div>
    )
  }
}