import React, { Component } from 'react'
import { Form } from '@ant-design/compatible';
import '@ant-design/compatible/assets/index.css';
import { Button, Card, Input, Checkbox } from 'antd';

import './style.css'

const FormItem = Form.Item

const residences = [{
  value: 'Jiangxi',
  label: '江西',
  children: [{
    value: 'Nanchang',
    label: '南昌',
    children: [{
      value: 'Qingshanhu',
      label: '青山湖'
    }]
  }]
}]

class SignForm extends Component {

  state = {
    confirmDirty: false,
  }

  handleConfirmBlur = (e) => {
    const value = e.target.value;
    this.setState({ confirmDirty: this.state.confirmDirty || !!value });
  }

  compareToFirstPassword = (rule, value, callback) => {
    const form = this.props.form;
    if (value && value !== form.getFieldValue('password')) {
      callback('两次密码不一致');
    } else {
      callback();
    }
  }

  validateToNextPassword = (rule, value, callback) => {
    const form = this.props.form;
    if (value && this.state.confirmDirty) {
      form.validateFields(['confirm'], { force: true });
    }
    callback();
  }

  handleSubmit = (e) => {
    e.preventDefault()
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        this.props.onSubmit()
      }
    })
  }


  render() {
    const { getFieldDecorator } = this.props.form
    const formItemLayout = {
      labelCol: {
        span: 6,
      },
      wrapperCol: {
        span: 18,
      },
    }
    const tailItemLayout = {
      wrapperCol: {
        span: 16,
        offset: 6,
      },
    }


    return (
      <Card className='sign-form' title={<div style={{ textAlign: 'center' }}>注册</div>} headStyle={{ justifyContent: "center" }}>
        <Form>
          <FormItem {...formItemLayout} label='手机号码'>
            {getFieldDecorator('phoneNumber', {
              rules: [{
                required: true, message: '请输入电话',
              }]
            })(
              <Input />
            )}
          </FormItem>
          <FormItem {...formItemLayout} label='密码'>
            {getFieldDecorator('password', {
              rules: [{
                required: true, message: '请输入密码',
              }, {
                validator: this.validateToNextPassword
              }]
            })(
              <Input type='password' />
            )}
          </FormItem>
          <FormItem {...formItemLayout} label='确认密码'>
            {getFieldDecorator('confirmPassword', {
              rules: [{
                required: true, message: '请确认密码',
              }, {
                validator: this.compareToFirstPassword
              }]
            })(
              <Input type='password' onBlur={this.handleConfirmBlur} />
            )}
          </FormItem>
          <FormItem {...tailItemLayout}>
            {getFieldDecorator('agreement', {
              valuePropName: 'checked',
              rules: [{required: true}]
            })(
              <Checkbox>我已经阅读<a href="./">用户协议</a></Checkbox>
            )}
          </FormItem>
          <FormItem {...tailItemLayout}>
            <Button onClick={this.handleSubmit} type='primary'>注册</Button>
          </FormItem>
        </Form>
      </Card>
    )
  }
}

export default Form.create({
  name: 'sign_state',
  mapPropsToFields(props) {
    let fields =  Object.keys(props.fields).map(key => [key, Form.createFormField({...props.fields[key]})])
    let o = {}
    for (let [key, value] of fields)  {
      o[key] = value
    }
    return o
  },
  onFieldsChange(props, changedFields) {
    props.onChange(changedFields)
  }
})(SignForm)