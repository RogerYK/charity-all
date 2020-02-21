import React, { Component } from 'react'

import './style.scss'
import { Steps, Button, message } from 'antd';

import {UserProtocol, IdentifyType, IdentifyInfo, WaitingInfo} from './ResortSteps'
import api from '../../api';


const Step = Steps.Step


export default class ResortPage extends Component {

  state = {
    current: 3,
    completed: [false, false],
    form: {
      identificationType: '',
      identityCardName: '',
      identityCardPicture: '',
      email: '',
      phoneNumber: '',
      sex: '',
      nation: 'China',
      province: '',
      city: '',
      region: '',
      detailAddress: '',
      company: '',
      introduction: '',
      profession: '',
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

  handleAgreeChange = values => {
    const state = this.state
    const completed = [...this.state.completed]
    completed[0] = values.completed
    this.setState({
      ...state,
      completed,
    })
  }

  handleIdentifyTypeChange = values => {
    console.log('typeChange', values)
    const preForm = this.state.form
    const completed = [...this.state.completed]
    completed[1] = values.completed
    const form = {
      ...preForm,
      identificationType: values.identificationType,
    }
    this.setState({
      ...this.state,
      completed,
      form,
    })
  }

  handleIdentifyInfoChange = values => {
    console.log(values)
    const preForm = this.state.form
    const form = {
      ...preForm,
      ...values,
    }
    this.setState({
      ...this.state,
      form,
    })
  }

  handleSubmit = () => {
    console.log('submit', this.state.form)
    api.User.identify(this.state.form).then(res => {
      this.setState({
        ...this.state,
        current: 3,
      })
    }).catch(res => {
      message.error(res.msg)
    })
  }

  render() {
    const { current, completed, form } = this.state
    const {handleAgreeChange, handleIdentifyTypeChange, handleIdentifyInfoChange, handleSubmit} = this

    const steps = [{
      title: '阅读协议',
      content: <UserProtocol agreed={completed[0]} onChange={handleAgreeChange} />,
    }, {
      title: '选择类型',
      content: <IdentifyType identificationType={form.identificationType} onChange={handleIdentifyTypeChange} />,
    }, {
      title: '认证资料登记',
      content: <IdentifyInfo values={form} onChange={handleIdentifyInfoChange} onSubmit={handleSubmit} />,
    }, {
      title: '等待审核',
      content: <WaitingInfo />,
    }, {
      title: '完成认证',
      content: <div></div>,
    }]

    return (
      <div className="resort-page">
        <div className="container">
          <Steps className="steps" current={current}>
            {steps.map(item => <Step key={item.title} title={item.title} />)}
          </Steps>
          <div className="steps-content">{steps[current].content}</div>
          <div className="steps-action">
            {current > 0 && current < 3 && <Button className="action" onClick={() => this.prev()}>返回上一步</Button>}
            {current < 2 && completed[current] && <Button onClick={() => this.next()}>确定，下一步</Button>}
          </div>
        </div>
      </div>
    )
  }
}