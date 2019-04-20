import React, { Component } from 'react'

import styles from './style.module.scss'
import { Divider, Form, Upload, Button, Input, Radio, DatePicker, Cascader, message } from 'antd';

import default_icon from './default_profile@3x.png'
import RadioGroup from 'antd/lib/radio/group';
import config from '../../../utils/config';
import { observer, inject } from 'mobx-react';
import commonStore from '../../../store/commonStore';



const addressOptions = [{
  value: '江西',
  label: '江西',
  children: [{
    value: '南昌',
    label: '南昌',
    children: [{
      value: '青山湖',
      label: '青山湖'
    }]
  }]
}]

class RowUserForm extends Component {

  constructor(props) {
    super(props)
    this.state = {
      avatarLoaded: false,
    }
  }

  handleAvatarChange = (info) => {
    console.log(info)

    if (info.fileList.length === 0) {
      this.setState({avatarLoaded: false})
      return
    }

    if (info.file.status === 'done') {
      if (info.file.response.errCode === 0) {
        info.file.url = info.file.response.data
        this.setState({avatarLoaded: true})
      } else {
        message.error('上传失败')
        info.file = null
      }
    }
    if (info.file.status === 'error') {
      message.error('上传失败')
    }
    info.fileList.slice(-1)
  }

  render() {
    const { getFieldDecorator } = this.props.form
    const formItemLayout = {
      labelCol: { span: 4 },
      wrapperCol: { span: 20 },
    }
    const avatarLoaded = this.state.avatarLoaded
    return (
      <Form >
        <Form.Item {...formItemLayout} label="头像">
          <div className={styles['avatar-field']}>
            <div className={styles['left']}>
              {getFieldDecorator('avatar')(
                <Upload
                  name="file"
                  headers={{ acess_token: commonStore.accessToken }}
                  action={config.baseURL + '/upload/'}
                  listType="picture-card"
                  onChange={this.handleAvatarChange}
                  multiple={false}
                >
                {avatarLoaded ? null : <img className={styles['avatar-img']} src={default_icon} alt="avatar" />}
                </Upload>
              )}
              <div className={styles['describe']}>点击左侧上传，比例1:1, 格式jpg、png、gif, 不大于100kb</div>
            </div>
          </div>
        </Form.Item>
        <Form.Item {...formItemLayout} label="昵称" extra="汉字、字母、数字、下划线">
          {getFieldDecorator('nickName')(
            <Input />
          )}
        </Form.Item>
        <Form.Item {...formItemLayout} label="性别">
          {getFieldDecorator('sex')(
            <RadioGroup>
              <Radio value={1}>男</Radio>
              <Radio value={2}>女</Radio>
            </RadioGroup>
          )}
        </Form.Item>
        <Form.Item {...formItemLayout} label="生日">
          {getFieldDecorator('birthday')(
            <DatePicker />
          )}
        </Form.Item>
        <Form.Item {...formItemLayout} label="居住地">
          {getFieldDecorator('address')(
            <Cascader options={addressOptions} />
          )}
        </Form.Item>
        <Form.Item {...formItemLayout} label="个人简介">
          {getFieldDecorator('presentation')(
            <Input.TextArea rows={5}></Input.TextArea>
          )}
        </Form.Item>
      </Form>

    )
  }
}


const UserForm = Form.create({
  name: 'state',
  mapPropsToFields(props) {
    let fields = Object.keys(props.fields).map(key => [key, Form.createFormField({ ...props.fields[key] })])
    let o = {}
    for (let [key, value] of fields) {
      o[key] = value
    }
    return o
  },
  onFieldsChange(props, changedFields) {
    props.onChange(changedFields)
  },
})(RowUserForm)

@inject('userStore')
@observer
export default class UserInfo extends Component {

  constructor(props) {
    super(props)
    this.state = {
      fields: {}
    }
  }

  handleChanges = (changedFields) => {
    console.log(changedFields)
    const fields = this.state.fields
    this.setState({
      fields: {
        ...fields,
        ...changedFields,
      }
    })
  }

  handleSubmit = () => {
    const fields = this.state.fields
    const data = {
      avatar: fields.avatar ? fields.avatar.value.file.url : undefined,
      nickName: fields.nickName ? fields.nickName.value : undefined,
      sex: fields.sex ? fields.sex.value : undefined,
      birthday: fields.birthday ? fields.birthday.value.format('YYYY-MM-DD HH:mm:ss') : undefined,
      address: fields.address ? fields.address.value.join(':') : undefined,
      presentation: fields.presentation ? fields.presentation.value : undefined,
    }
    console.log(data)
    this.props.userStore.updateUser(data)
  }

  render() {
    return (
      <div className={styles['user-info']}>
        <div className={styles['header']}>
          <span className={styles['title']}>个人资料</span>
        </div>
        <Divider />
        <div className={styles['content']}>
          <UserForm onChange={this.handleChanges} fields={this.state.fields} />
          <div className={styles['submit-wrapper']}>
            <Button onClick={this.handleSubmit} style={{ float: 'right' }} type="primary">保存</Button>
          </div>
        </div>
      </div>
    )
  }
}
