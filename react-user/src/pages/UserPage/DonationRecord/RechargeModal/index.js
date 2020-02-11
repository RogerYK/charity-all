import React, { Component } from 'react'
import { Modal, Form, InputNumber, Button, message } from 'antd'
import api from '../../../../api'


class RechargeModal extends Component {

  handleSubmit = ({amount}) => {
    api.Transaction.recharge(amount).then(res => {
      message.success('充值成功')
      this.props.onCancel()
    }).catch(res => {
      message.error(res.msg)
    })
  }


  render() {
    const {visible, onCancel} = this.props
    return (
      <Modal
        visible={visible}
        onCancel={oncancel}
        title="充值"
        footer={null}
      >
        <Form onFinish={this.handleSubmit}>
          <Form.Item name="amount" label="金额(元)" rules={[{required: true, type: 'number',min: 1}]}>
            <InputNumber />
          </Form.Item>
          <Button htmlType="submit" type="primary" >提交</Button>
          <Button onClick={onCancel} type="danger">取消</Button>
        </Form>
      </Modal>
    )
  }
}


export default RechargeModal