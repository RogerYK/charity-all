import React, {Component} from 'react'
import { Form } from '@ant-design/compatible';
import '@ant-design/compatible/assets/index.css';
import {  Button, message } from 'antd';
import { observable, action } from 'mobx';
import api from '../../../../api';
import { observer } from 'mobx-react';
import ReactQuill from 'react-quill';

@observer
export default class ScheduleForm extends Component {

  @observable content = ''

  @action
  handleChange = (v) => {
    this.content = v
  }

  submit = () => {
    const projectId = this.props.projectId
    api.Project
      .addSchedule({projectId, content: this.content})
      .then(() => {
        message.success('添加成功')
        this.props.onCancel()
      }).catch(res => {
        message.error(res.msg)
        this.props.onCancel()
      })

  }

  render() {
    const {content, handleChange, submit} = this
    const Item = Form.Item
    const itemLayout = {labelCol: {span: 4}, wrapperCol: {span: 20}}
    const tailLayout = {wrapperCol: {span: 20, offset: 4}}
    return (
      <Form>
        <Item {...itemLayout} label="进展情况">
          <ReactQuill value={content} onChange={handleChange}/>
        </Item>
        <Item {...tailLayout}>
          <Button type="primary" onClick={submit} style={{marginRight: 20}}>保存</Button>
          <Button onClick={this.props.onCancel}>关闭</Button>
        </Item>
      </Form>
    )
  }
}