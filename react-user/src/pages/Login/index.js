import React, {Component} from 'react'
import {Carousel, message} from 'antd'

import styles from './style.module.scss'
import b1 from './bbccn-bg-1.jpg'
import b2 from './bbccn-bg-2.jpg'
import b3 from './bbccn-bg-3.jpg'
import LoginForm from './LoginForm';
import api from '../../api'
import { observer, inject } from 'mobx-react';

const bgImgs = [b1, b2, b3]

@inject("commonStore")
@observer
export default class LoginPage extends Component {

  componentDidMount() {
    window.scrollTo(0, 0);
  }

  handleSubmit = values => {
    console.log(values);
    api.Auth.login(values)
      .then(res => {
        message.success("登陆成功");
        this.props.commonStore.setToken(res.data);
        setTimeout(() => {
          this.props.history.push("/");
        }, 1000);
      })
      .catch(res => {
        message.error("登陆失败");
      })
  }

  render() {
    return (
      <div className={styles["login-container"]}>
        <div className={styles["background"]}>
          <Carousel effect="fade" dots={false} autoplay={true}>
            {bgImgs.map((img, i) => (
              <img key={i} src={img} alt="img" />
            ))}
          </Carousel>
        </div>
        <LoginForm className={styles.form} onSubmit={this.handleSubmit} />
      </div>
    )
  }
}
