import React, { Component } from 'react'
import { Card, Form, Input, Button, Icon, Checkbox} from 'antd'


const FormItem = Form.Item

class RowLoginForm extends Component {

  handleSubmit = (e) => {
    e.preventDefault()
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        this.props.onSubmit(values)
      }
    })
  }

  render() {
    const { getFieldDecorator } = this.props.form
    return (
      <div className={this.props.className}>
        <Card title={<div style={{ textAlign: "center" }}>登陆</div>} headStyle={{ justifyContent: 'center' }}>
          <Form>
            <FormItem>
              {getFieldDecorator('phoneNumber',{
                rules: [{
                  required: true,
                  message: '手机号未填写'
                }, {
                  len: 11,
                  message: '手机号11位'
                }]
              })(
              <Input
                prefix={<Icon type='user'
                  style={{ color: 'rgba(0,0,.25)' }} />}
                placeholder='手机号'
              />)}
            </FormItem>
            <FormItem>
              {getFieldDecorator('password',{
                rules: [{
                  required: true,
                  message: '密码未填写',
                }, {
                  min: 6,
                  message: '密码最低6位'
                }, {
                  max: 30,
                  message: '密码最长30位'
                }]
              })(<Input.Password
                prefix={<Icon type='lock'
                  style={{ color: 'rgba(0,0,.25)' }} />}
                placeholder='密码'
              >
              </Input.Password>
              )}
            </FormItem>
            <FormItem>
              <a href=".">忘记密码</a>
            </FormItem>
            <Button onClick={this.handleSubmit} type='primary' className='login-submit-btn'>登陆</Button>
          </Form>
        </Card>
      </div>
    )
  }
}

const LoginForm = Form.create()(RowLoginForm)
export default LoginForm


