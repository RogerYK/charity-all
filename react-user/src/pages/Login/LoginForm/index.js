import React, { Component } from 'react'
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Card, Input, Button, Form } from 'antd';


const FormItem = Form.Item

class RowLoginForm extends Component {

  render() {
    const {onSubmit} = this.props
    return (
      <div className={this.props.className}>
        <Card title={<div style={{ textAlign: "center" }}>登陆</div>} headStyle={{ justifyContent: 'center' }}>
          <Form onFinish={onSubmit}>
            <FormItem
              name="phoneNumber"
              rules={[{
                required: true,
                message: '手机号未填写'
              }, {
                len: 11,
                message: '手机号11位'
              }]}>
              <Input 
                prefix={<UserOutlined style={{ color: 'rgba(0,0,.25)' }} />} 
                placeholder='手机号'
              />
            </FormItem>
            <FormItem
              name="password"
              rules={[{
                required: true,
                message: '密码未填写',
              }, {
                min: 6,
                message: '密码最低6位'
              }, {
                max: 30,
                message: '密码最长30位'
              }]}>
              <Input.Password
                prefix={<LockOutlined style={{ color: 'rgba(0,0,.25)' }} />}
                placeholder='密码'
              >
              </Input.Password>
            </FormItem>
            <FormItem>
              <a href=".">忘记密码</a>
            </FormItem>
            <Button htmlType="submit" type='primary' className='login-submit-btn'>登陆</Button>
          </Form>
        </Card>
      </div>
    );
  }
}

const LoginForm = RowLoginForm
export default LoginForm


