import React, { Component } from 'react'
import { Button, Card, Input, Checkbox, Form } from 'antd';

import './style.css'

const FormItem = Form.Item


class SignForm extends Component {

  formRef = React.createRef()

  state = {
    confirmDirty: false,
  }

  handleConfirmBlur = (e) => {
    const value = e.target.value;
    this.setState({ confirmDirty: this.state.confirmDirty || !!value });
  }

  compareToFirstPassword = (rule, value, callback) => {
    return new Promise( (resolve, reject) => {
      const form = this.formRef.current;
      if (value && value !== form.getFieldValue('password')) {
        reject('两次密码不一致');
      } else {
        resolve()
      }
    })
  }

  validateToNextPassword = (rule, value, callback) => {
    return new Promise((resolve => {
      const form = this.formRef.current;
      if (value && this.state.confirmDirty) {
        form.validateFields(['confirm'], { force: true });
      }
      resolve();
    }))
  }


  render() {
    const {onSubmit} = this.props
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
        <Form ref={this.formRef} onFinish={onSubmit}>
          <FormItem
            {...formItemLayout}
            name="phoneNumber"
            label='手机号码'
            rules={[{
              required: true, message: '请输入电话',
            }, {
              pattern: /\d{11}/g, message: '格式不对',
            }]}
          >
            <Input />
          </FormItem>
          <FormItem
            {...formItemLayout}
            name="password"
            label='密码'
            rules={[{
              required: true, message: '请输入密码',
            }, {
              min: 6,
              message: '密码最低6位'
            }, {
              max: 30,
              message: '密码最多30位'
            }, {
              validator: this.validateToNextPassword
            }]}
          >
            <Input.Password  type='password' />
          </FormItem>
          <FormItem
            {...formItemLayout}
            label='确认密码'
            name="confirm"
            rules={[{
              required: true, message: '请确认密码',
            }, {
              validator: this.compareToFirstPassword
            }, {
              min: 6,
              message: '密码最低6位'
            }, {
              max: 30,
              message: '密码最多30位'
            }]}
          >
            <Input.Password type='password' onBlur={this.handleConfirmBlur} />
          </FormItem>
          <FormItem
            {...tailItemLayout}
            name="agreement"
            rules={[{required: true,
              message: '请同意用户协议'
            }]}
          >
            <Checkbox.Group>
              <Checkbox value="agree">我已经阅读<a href="./">用户协议</a></Checkbox>
            </Checkbox.Group>
          </FormItem>
          <FormItem {...tailItemLayout}>
            <Button htmlType="submit" type='primary'>注册</Button>
          </FormItem>
        </Form>
      </Card>
    )
  }
}

export default SignForm