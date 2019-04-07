import React, { Component } from 'react'
import Header from '../../components/Header';
import Footer from '../../components/Footer';
import {Carousel, message} from 'antd'

import './style.scss'
import SignForm from './SignForm';

import b1 from './bbccn-bg-1.jpg'
import b2 from './bbccn-bg-2.jpg'
import b3 from './bbccn-bg-3.jpg'
import { sign } from '../../api';

const bgImgs = [b1, b2, b3]

export default class SignPage extends Component {

    constructor(props) {
      super(props)
      this.state = {
        phoneNumber: {value: ''},
        password: {value: ''},
      }
    }

    componentDidMount() {
        window.scrollTo(0, 0)
    }

    handleChange = (changedFields) => {
      console.log(changedFields)
      this.setState((state) => ({
        ...state,
        ...changedFields,
      }))
    }

    onSubmit = () => {
      console.log(this.state)
      const {phoneNumber, password} = this.state
      console.log(phoneNumber.value, password.value)
      sign(phoneNumber.value, password.value).then(() => {
        message.success('注册成功')
        setTimeout(() => {
          this.props.history.push('/login')
        })
      }).catch((res) => {
        message.error(res.msg)
      })
    }

    render() {
        return (
            <div className='sign-page'>
                <Header />
                <div className='sign-container'>
                    <div className="background-wrap">
                        <Carousel effect="fade" dots={false} autoplay={true}>
                            {bgImgs.map((img, i) => <img key={i} className="bg-img" src={img} alt="im" />)}
                        </Carousel>
                    </div>
                    <SignForm fields={this.state} onChange={this.handleChange} 
                      onSubmit={this.onSubmit}
                    />
                </div>
                <Footer />
            </div>
        )
    }
}
