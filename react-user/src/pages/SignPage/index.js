import React, { Component } from 'react'
import {Carousel, message} from 'antd'

import styles from './style.module.scss'
import SignForm from './SignForm';

import b1 from './bbccn-bg-1.jpg'
import b2 from './bbccn-bg-2.jpg'
import b3 from './bbccn-bg-3.jpg'
import api from '../../api';

const bgImgs = [b1, b2, b3]

export default class SignPage extends Component {

  constructor(props) {
    super(props)
    this.state = {
      phoneNumber: {value: ''},
      password: {value: ''},
    }
  }
  
  handleChange = (changedFields) => {
    console.log(changedFields)
    this.setState((state) => ({
      ...state,
      ...changedFields,
    }))
  }

  onSubmit = (values) => {
    console.log(values)
    api.Auth.sign({phoneNumber:values.phoneNumber, password: values.password})
      .then(() => {
        message.success('注册成功')
        setTimeout(() => {
          this.props.history.push('/login')
        })
      })
  }

  render() {
    return (
      <div className={styles['sign-container']}>
        <div className={styles["background-wrap"]}>
          <Carousel effect="fade" dots={false} autoplay={true}>
            {bgImgs.map((img, i) => <img key={i} className={styles["bg-img"]} src={img} alt="im" />)}
          </Carousel>
        </div>
        <SignForm fields={this.state} onChange={this.handleChange}
          onSubmit={this.onSubmit}
        />
      </div>
    )
  }
}
