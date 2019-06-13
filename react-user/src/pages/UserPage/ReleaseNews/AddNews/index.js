import React, { Component } from 'react'
import { Form, Input, Upload, Button, Icon, message } from 'antd';
import { observer } from 'mobx-react';
import { observable, action } from 'mobx';
import ReactQuill from 'react-quill';
import config from '../../../../utils/config';
import api from '../../../../api';

import styles from './style.module.scss'

@observer
export default class AddNewsForm extends Component {

  @observable form = {
    title: '',
    content: '',
    introduce: ''

  }

  @observable imgList = []

  @action
  handleChange = (key, e, value) => {
    const v = e ? e.target.value : value
    this.form[key] = v
  }

  @action
  handleImgListChange = (info) => {
    const imgList = info.fileList.filter(f => f.status !== 'error')
    this.imgList = imgList
  }

  handleSubmit = () => {
    const img = this.imgList[0].response.data
    const data = {
      ...this.form,
      img
    }
    api.News.save(data)
      .then(res => {
        message.success('发布成功')
      }).catch(res => {
        message.error(res.msg)
      })
    this.props.onCancel()
  }

  
  render() {
    const Item = Form.Item
    const formItemLayout = {labelCol: {span: 4}, wrapperCol: {span: 20}}
    const tailItemLayout = {wrapperCol: {span: 20, offset: 4}}
    const {
      form,
      imgList,
      handleChange,
      handleImgListChange,
      handleSubmit,
    } = this
    const {
      title,
      introduce,
      content
    } = form
    return (
      <Form>
        <Item {...formItemLayout} label="标题">
          <Input value={title} onChange={e => handleChange('title', e)} />
        </Item>
        <Item {...formItemLayout} label="图片">
          <Upload
            action={`${config.baseURL}/upload/`}
            fileList={imgList}
            onChange={handleImgListChange}
            disabled={imgList.length !== 0}
          >
            <Button>
              <Icon type="upload" />点击上传图片
            </Button>
          </Upload>
        </Item>
        <Item {...formItemLayout} label="介绍">
          <Input value={introduce} onChange={e => handleChange('introduce', e)} />
        </Item>
        <Item {...formItemLayout} label="内容">
          <ReactQuill 
            value={content}
            onChange={v => handleChange('content', null, v)}
          />
        </Item>
        <Item {...tailItemLayout}>
          <Button className={styles['action-btn']} onClick={handleSubmit} type="primary">发布</Button>
          <Button className={styles['action-btn']} onClick={this.props.onCancel} >取消</Button>
        </Item>
      </Form>
    )
  }
}