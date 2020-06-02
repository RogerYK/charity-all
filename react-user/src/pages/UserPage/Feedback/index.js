import React, { Component } from 'react'

import styles from './style.module.scss'
import { Form } from '@ant-design/compatible';
import '@ant-design/compatible/assets/index.css';
import { Input, Select, Button, Divider } from 'antd';

export default class FeedBack extends Component {

  constructor(props) {
    super(props)
    this.state = {
      problem: undefined,
      message: ''
    }
  }

  handleProblemChange = (e) => {
    this.setState({problem:e.target.value})
  }

  handleMessageChange = (e) => {
    this.setState({message: e.target.value})
  }

  getform() {
    const Option = Select.Option
    const formItemLayout = {wrapperCol: {span: 20, offset: 2}}
    return (
      <Form>
        <Form.Item {...formItemLayout}>
          <Select size="large" placeholder="选择问题类型" value={this.state.problem} onChange={this.handleProblemChange}>
            <Option value={0}>登陆、注册</Option>
            <Option value={1}>账号设置</Option>
            <Option value={2}>用户认证</Option>
            <Option value={3}>投诉举报</Option>
            <Option value={4}>项目发布</Option>
            <Option value={5}>捐款、提现</Option>
          </Select>
        </Form.Item>
        <Form.Item {...formItemLayout}>
          <Input.TextArea
            placeholder="在此留言您需要反馈的问题，建议尽可能描述清楚步骤并附图说明，客服人员将在三个工作日答复你"
            rows={8} 
            value={this.state.message}
            onChange={this.handleMessageChange}>
          </Input.TextArea>
        </Form.Item>
        <Form.Item {...formItemLayout}>
          <div className={styles['submit-wrapper']}>
            <Button type="primary">确认提交</Button>
          </div>
        </Form.Item>
      </Form>
    )
  }



  render() {
    return (
      <div className={styles['feedback']}>
        <div className="title">
          客服留言
        </div>
        <div className={styles['content']}>
          {this.getform()}
        </div>
      </div>
    )
  }
}