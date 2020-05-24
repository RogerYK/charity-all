import React, { Component } from 'react'
import { Input, Button, Form } from 'antd';
import { observer } from 'mobx-react';
import { observable, action } from 'mobx';

@observer
class Editor extends Component {

  @observable value = ''

  @action
  handleChange = (e) => {
    this.value = e.target.value
  }

  @action
  handleFocus = () => {
    this.props.onFocus()
  }

  @action
  handleSubmit = () => {
    if (this.value === '') return

    this.props.onSubmit(this.value)
  }

  render() {
    const {logined, submitting} = this.props

    return (
      <div>
        <Form.Item style={{width: '100%'}}>
          <Input.TextArea
            placeholder={logined ? "" : "请登陆后评论"}
            rows={4}
            onChange={this.handleChange}
            value={this.value}
            onFocus={this.handleFocus}
          />
        </Form.Item>
        <Form.Item>
          <Button disabled={!logined} loading={submitting} onClick={this.handleSubmit}>
          评论
          </Button>
        </Form.Item>
      </div>
    )
  }
}

@observer
class EditorLine extends Component {

  @observable value = ''


  @action
  handleValueChange = (e) => {
    this.value = e.target.value
  }

  @action
  handleSubmit = () => {
    if (this.value === '') return

    this.props.onSubmit(this.value)
  }

  render() {
    const {value, handleValueChange, handleSubmit} = this
    return (
      <div>
        <Input value={value} onChange={handleValueChange} 
          suffix={<span onClick={handleSubmit}>发送</span>} />
      </div>
    )
  }
}

export {Editor, EditorLine}