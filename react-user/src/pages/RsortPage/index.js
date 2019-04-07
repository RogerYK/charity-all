import React, { Component } from 'react'

import Header from '../../components/Header'
import Footer from '../../components/Footer'


import './style.scss'
import { Steps, Checkbox, Button, Radio, Row, Col, Collapse, Form, Input, Upload, Icon, Cascader, Select } from 'antd';
import RadioGroup from 'antd/lib/radio/group';

const FormItem = Form.Item

const Step = Steps.Step
const Panel = Collapse.Panel

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 10 },
}

const imgUploadProps = {
  name: 'personalImg',
  action: '//img/upload',
  accept: 'image/*',
  listType: 'picture-card'
}

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

const steps = [{
  title: '阅读协议',
  content: (
    <div className="resort-protocol steps-form">
      <div className="title">用户协议</div>
      <div className="protocol content">
        <h3>1.重要须知</h3>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <h3>2.保护条款</h3>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <h3>3.用户义务</h3>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <h3>4.用户须知</h3>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
        <p>1.1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p>
      </div>
      <div className="checkbox">
        <Checkbox >我已阅读并同意《<a href=".">用户协议</a>》</Checkbox>
      </div>
    </div>
  )
}, {
  title: '选择类型',
  content: (
    <div className="identify-type steps-form">
      <div className="title">
        请选择认证类型
            </div>
      <Row type="flex" className="identify-list" justify="space-between">
        <Col className="item" span={6}>
          <div className="panel">
            <div className="img personal"></div>
            <div className="name">个人认证</div>
            <div className="desc">适合以个人身份发起项目需实名认证</div>
          </div>
          <Radio />
        </Col>
        <Col className="item" span={6}>
          <div className="panel">
            <div className="img non-organization"></div>
            <div className="name">非公募机构认证</div>
            <div className="desc">适合慈善组织(无公开募捐资格证书)、民办非企业单位、社会团体、企业等，不具备直接面向公众募捐的资格</div>
          </div>
          <Radio />
        </Col>
        <Col className="item" span={6}>
          <div className="panel">
            <div className="img organization"></div>
            <div className="name">公募机构认证</div>
            <div className="desc">适合民政部批准，持有法人登记证书、公开募捐资格证书，具备向公众募捐资金资质的公募基金会</div>
          </div>
          <div className="radio-box">
            <Radio />
          </div>
        </Col>
      </Row>
    </div>
  )
}, {
  title: '认证资料登记',
  content: (
    <div className="identify-info">
      <Collapse bordered={false} defaultActiveKey={['1']}>
        <Panel className="custom-panel personal-identify" header="信息登记" key="1">
          <div className="custom-panel-content">
            <Form className="form">
              <FormItem {...formItemLayout} label="认证类型">
                <span className="identify-type">个人认证</span>
              </FormItem>
              <FormItem {...formItemLayout} label="身份证姓名">
                <Input />
              </FormItem>
              <FormItem {...formItemLayout} label="身份证号码">
                <Input />
              </FormItem>
              <FormItem {...formItemLayout} label="身份证半身照">
                <Upload {...imgUploadProps} >
                  <div className="img-upload">
                    <Icon type="plus" />
                    <div className="upload-text">个人图片</div>
                  </div>
                </Upload>
              </FormItem>
              <FormItem {...formItemLayout} label="联系邮箱">
                <Input />
              </FormItem>
              <FormItem {...formItemLayout} label="手机号码">
                <Input />
              </FormItem>
              <FormItem {...formItemLayout} label="短信验证码">
                <Input className="phone-msg-input" />
                <Button>发送手机验证码</Button>
              </FormItem>
              <Button type="primary" className="save-btn">保存继续</Button>
            </Form>
          </div>
        </Panel>
        <Panel className="custom-panel" header="用户资料" key="2">
          <div className="custom-panel-content">
            <Form className="form">
              <FormItem {...formItemLayout} label="头像">
                <Upload {...imgUploadProps} >
                  <div className="img-upload">
                    <Icon type="plus" />
                    <div className="upload-text">个人图片</div>
                  </div>
                </Upload>
              </FormItem>
              <FormItem {...formItemLayout} label="性别">
                <RadioGroup value="man">
                  <Radio value="man">男</Radio>
                  <Radio value="woman">女</Radio>
                </RadioGroup>
              </FormItem>
              <FormItem {...formItemLayout} label="所在地">
                <Cascader options={residences} placeholder="选择地区"></Cascader>
              </FormItem>
              <FormItem {...formItemLayout} label="详细地址">
                <Input />
              </FormItem>
              <FormItem {...formItemLayout} label="职业">
                <Select placeholder="请选择">
                  <Select.Option value="">在职员工</Select.Option>
                  <Select.Option value="">在读学生</Select.Option>
                  <Select.Option value="">个体户</Select.Option>
                  <Select.Option value="">务农</Select.Option>
                  <Select.Option value="">退休</Select.Option>
                  <Select.Option value="">自由职业</Select.Option>
                </Select>
              </FormItem>
              <FormItem {...formItemLayout} label="所在单位">
                <Input />
              </FormItem>
              <FormItem {...formItemLayout} label="简介">
                <textarea className="brief-intro-area" rows="3"></textarea>
                <div className="brief-intro">
                  请简单介绍自己的背景，最多200个字<br />示例1：我是腾讯大粤网记者王海，平时热心公益，在平时采访中发现许多需要帮助的人，希望通过腾讯公益平台帮到他们
                            </div>
              </FormItem>
            </Form>
          </div>
        </Panel>
      </Collapse>
    </div>
  ),
}, {
  title: '等待审核',
  content: (
    <div className="verify-timeline steps-form">
      <div className="title">信息已上传，请等待审核通过!</div>
    </div>
  ),
}, {
  title: '完成认证',
  content: <div></div>,
}]

export default class ResortPage extends Component {

  constructor(props) {
    super(props)
    this.state = {
      current: 0,
    }
  }

  componentDidMount() {
    window.scrollTo(0, 0)
  }

  next() {
    const current = this.state.current + 1
    this.setState({ current })
  }

  prev() {
    const current = this.state.current - 1
    this.setState({ current })
  }

  render() {
    const { current } = this.state

    return (
      <div className="resort-page">
        <Header />
        <div className="container">
          <Steps className="steps" current={current}>
            {steps.map(item => <Step key={item.title} title={item.title} />)}
          </Steps>
          <div className="steps-content">{steps[current].content}</div>
          <div className="steps-action">
            {current > 0 && <Button className="action" onClick={() => this.prev()}>返回上一步</Button>}
            {current < steps.length - 1 && <Button onClick={() => this.next()}>确定，下一步</Button>}
          </div>
        </div>
        <Footer />
      </div>
    )
  }
}