import React, {Component} from 'react'
import {Carousel, message} from 'antd'
import Header from '../../components/Header';
import Footer from '../../components/Footer';

import './style.css'
import b1 from './bbccn-bg-1.jpg'
import b2 from './bbccn-bg-2.jpg'
import b3 from './bbccn-bg-3.jpg'
import { LoginForm } from './LoginForm';
import { login } from '../../api';
import { setToken } from '../../utils/auth';

const bgImgs = [b1, b2, b3]

export default class LoginPage extends Component {

    componentDidMount() {
        window.scrollTo(0, 0)
    }

    handleSubmit = (values) => {
      console.log(values)
      login(values.phoneNumber, values.password).then(res => {
        message.success('登陆成功')
        setToken(res.data)
        setTimeout(() => {
          this.props.history.push('/')
        }, 1000)
      }).catch(res => {
        message.error('登陆失败')
      })
    }

    render() {
        return (
            <div className='login-page'>
                <Header />
                <div className='login-container'>
                    <div className='background'>
                        <Carousel effect='fade' dots={false} autoplay={true} >
                            {bgImgs.map((img, i) => <img key={i} src={img} alt='img' />)}
                        </Carousel>
                    </div>
                    <LoginForm onSubmit={this.handleSubmit} />
                </div>
                <Footer />
            </div>
        )
    }
}
