import React, { Component } from 'react'
import { observer, inject } from 'mobx-react';
import {observable, action} from 'mobx'

import styles from './style.module.scss'
import { InputNumber, Button,Modal } from 'antd';

@inject('detailStore')
@observer
export default class DonationModal extends Component {

  @observable amount = 1

  donate = () => {
    const {projectId, onCancel} = this.props;
    const amount = this.amount
    const detailStore = this.props.detailStore
    detailStore.donate(projectId, amount)
    onCancel()
  }

  @action
  handleAmountChange = value => this.amount = value
  
  render() {
    const {amount, handleAmountChange, donate} = this
    const { visiable ,onCancel} = this.props
    return (
      <Modal
        visible={visiable}
        onCancel={onCancel}
        title="捐款"
        footer={null}
      >
        <div className={styles['content']}>
          <div className={styles['item']}>
            <div className={styles['label']}>捐款金额:</div>
            <div className={styles['field']}>
              <InputNumber 
                className={styles['input']}
                value={amount}
                min={1}
                onChange={handleAmountChange}
                precision={0}
              />
              <span>元</span>
            </div>
          </div>
          <div className={styles['tail']}>
            <Button onClick={donate} type="primary">确认</Button>
            <Button onClick={onCancel} type="danger">取消</Button>
          </div>
        </div>
      </Modal>
    )
  }
}