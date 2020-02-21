import React, { Component } from 'react'

import {Link} from 'react-router-dom'

import { PlusOutlined } from '@ant-design/icons';

import config from '../../../utils/config'

import {
  Form,
  Checkbox,
  Row,
  Col,
  Radio,
  Input,
  Select,
  Collapse,
  Cascader,
  Upload,
  Button,
} from 'antd';

import styles from './style.module.scss'

const FormItem = Form.Item
const Panel = Collapse.Panel
const RadioGroup = Radio.Group

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 10 },
}

const imgUploadProps = {
  action: `${config.baseURL}/upload/`,
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

class UserProtocol extends Component {


  handleChange = e => {
    const { onChange } = this.props
    onChange({completed: e.target.checked})
  }

  render() {
    const {agreed} = this.props
    return (
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
          <Checkbox checked={agreed} onChange={this.handleChange}>我已阅读并同意《<a href=".">用户协议</a>》</Checkbox>
        </div>
      </div>
    );
  }
}


class IdentifyType extends Component {

  handleChange = e => {
    const {onChange} = this.props
    onChange({completed: true, identificationType: e.target.value})
  }

  render() {
    const {identificationType} = this.props
    const {handleChange} = this
    return (
      <div className="identify-type steps-form">
        <div className="title">请选择认证类型</div>
        <Radio.Group style={{width: '100%', paddingLeft: '20%', paddingRight: '20%'}} value={identificationType} onChange={handleChange}>
          <Row type="flex" className="identify-list" justify="space-between">
            <Col className="item" span={6}>
              <div className="panel">
                <div className="img personal"></div>
                <div className="name">个人认证</div>
                <div className="desc">适合以个人身份发起项目需实名认证</div>
              </div>
              <Radio value="Personal" />
            </Col>
            <Col className="item" span={6}>
              <div className="panel">
                <div className="img organization"></div>
                <div className="name">公募机构认证</div>
                <div className="desc">适合民政部批准，持有法人登记证书、公开募捐资格证书，具备向公众募捐资金资质的公募基金会</div>
              </div>
              <div className="radio-box">
                <Radio value="Organization" />
              </div>
            </Col>
          </Row>
        </Radio.Group>
      </div>
    )
  }
}


const IntroductionArea = ({value, onChange}) => {
  const triggerChange = changeValue => {
    if (onChange) {
      onChange(changeValue)
    }
  }
  const handleChange = e => {
    triggerChange(e.target.value)
  }
  return (
    <>
      <Input.TextArea value={value} onChange={handleChange} className="brief-intro-area" rows="3" />
      <div className="brief-intro">
        请简单介绍自己的背景，最多200个字<br />示例1：我是腾讯大粤网记者王海，平时热心公益，在平时采访中发现许多需要帮助的人，希望通过腾讯公益平台帮到他们
      </div>
    </>
  )
}

class IdentifyInfo extends Component {

  state = {
    activeKey: '1',
  }

  mapValuesToFields(values) {
    values = {...values}
    const fields = []
    const place = {name: ['place'], value: []}
    for (const k of ['province', 'city', 'region']) {
      place.value.push(values[k])
      delete values[k]
    }
    for (const k in values) {
      fields.push({name: [k], value: values[k]})
    }
    return fields
  }

  mapFieldsToValues(fields) {
    let values = {}
    for (const f of fields) {
      if (f.name[0] === "place") {
        values['province'] = f.value[0]
        values['city'] = f.value[1]
        values['region'] = f.value[2]
      } else {
        const key = f.name.reduce((a, b) => a + b)
        values[key] = f.value
      }
    }
    return values
  }

  handlePreFinish = () => {
    this.setState({
      activeKey: '2',
      imgUrl: null,
    })
  }

  handleChange = (fields) => {
    console.log(fields)
    const {values, onChange} = this.props
    const newValues = {
      ...values,
      ...this.mapFieldsToValues(fields),
    }
    onChange(newValues)
  }

  handleAfterFinsh= () => {
    const {onSubmit} = this.props
    onSubmit()
  }

  handleCollapseChange = (key) => {
    if (this.state.activeKey === "2") {
      this.setState({activeKey: key})
    }
  }

  handleImgChange = info => {
    if (info.file.status === 'done') {
      const imgUrl = info.file.response.data
      this.setState({
        ...this.state,
        imgUrl,
      })
      this.handleChange([{name: ['identityCardPicture'], value: imgUrl}])
    }
  }


  render() {
    const {activeKey, imgUrl} = this.state
    const {handleChange, handlePreFinish, handleAfterFinsh, handleCollapseChange} = this
    const fields = this.mapValuesToFields(this.props.values)
    return (
      <div className="identify-info">
        <Collapse accordion bordered={false} activeKey={activeKey} onChange={handleCollapseChange}>
          <Panel className="custom-panel personal-identify" header="信息登记" key="1">
            <div className="custom-panel-content">
              <Form fields={fields} onFieldsChange={handleChange} className="form" onFinish={handlePreFinish}>
                <FormItem {...formItemLayout} label="认证类型">
                  <span className="identify-type">个人认证</span>
                </FormItem>
                <FormItem name="identityCardName" {...formItemLayout} label="身份证姓名">
                  <Input />
                </FormItem>
                <FormItem name="identityCardNumber" {...formItemLayout} label="身份证号码">
                  <Input />
                </FormItem>
                <FormItem  {...formItemLayout} label="身份证半身照">
                  <Upload {...imgUploadProps} onChange={this.handleImgChange} >
                    <div className="img-upload">
                      {imgUrl ? null : <PlusOutlined /> }
                      <div className="upload-text">个人图片</div>
                    </div>
                  </Upload>
                </FormItem>
                <FormItem name="email" {...formItemLayout} label="联系邮箱">
                  <Input />
                </FormItem>
                <FormItem name="phoneNumber" {...formItemLayout} label="手机号码">
                  <Input />
                </FormItem>
                <FormItem name="verifyCode" {...formItemLayout} label="短信验证码">
                  <Input className="phone-msg-input" />
                  <Button>发送手机验证码</Button>
                </FormItem>
                <Button htmlType="submit" type="primary" className="save-btn">保存继续</Button>
              </Form>
            </div>
          </Panel>
          <Panel className="custom-panel" header="用户资料" key="2">
            <div className="custom-panel-content">
              <Form fields={fields} onFieldsChange={handleChange} className="form" onFinish={handleAfterFinsh}>
                <FormItem name="sex" {...formItemLayout} label="性别">
                  <RadioGroup value="man">
                    <Radio value="Man">男</Radio>
                    <Radio value="Woman">女</Radio>
                  </RadioGroup>
                </FormItem>
                <FormItem name="place" {...formItemLayout} label="所在地">
                  <Cascader options={residences} placeholder="选择地区"></Cascader>
                </FormItem>
                <FormItem name="detailAddress" {...formItemLayout} label="详细地址">
                  <Input />
                </FormItem>
                <FormItem name="profession" {...formItemLayout} label="职业">
                  <Select placeholder="请选择">
                    <Select.Option value="employee">在职员工</Select.Option>
                    <Select.Option value="student">在读学生</Select.Option>
                    <Select.Option value="individual">个体户</Select.Option>
                    <Select.Option value="farmer">务农</Select.Option>
                    <Select.Option value="retire">退休</Select.Option>
                    <Select.Option value="liberal">自由职业</Select.Option>
                  </Select>
                </FormItem>
                <FormItem name="company" {...formItemLayout} label="所在单位">
                  <Input />
                </FormItem>
                <FormItem name="introduction" {...formItemLayout} label="简介">
                  <IntroductionArea />
                </FormItem>
                <Button htmlType="submit" type="primary" className="save-btn">保存继续</Button>
              </Form>
            </div>
          </Panel>
        </Collapse>
      </div>
    );
  }
}


const WaitingInfo = props => (
  <div className={styles.waitingInfo}>
    <div className={styles.title}>信息已上传，请等待审核通过!</div>
    <div className={styles.actions}><Link to="/" ><Button>返回首页</Button></Link></div>
  </div>
)


export { UserProtocol, IdentifyType, IdentifyInfo, WaitingInfo} 