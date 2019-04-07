import React, { Component } from 'react'
import { Link, Switch, Route } from 'react-router-dom'

import { Menu, Input } from 'antd'

import Carousel from '../../components/Carousel'

import styles from './style.module.scss'

import Header from '../../components/Header';
import Footer from '../../components/Footer';
import IndexShow from './IndexShow';

import {getRecommendBanners} from '../../api'
import RankShow from './RankShow';

const Search = Input.Search

export default class DonationPage extends Component {

  constructor(props) {
    super(props)
    this.state = { 
      current: 'index',
      banners: []
    }
  }

  componentDidMount() {
    window.scrollTo(0, 0)
    getRecommendBanners().then((res) => {
      if (res.errCode === 0) {
        this.setState({banners: res.data})
      }
    })
  }

  handleSwitch = (e) => {
    this.setState({ current: e.key })
  }

  render() {
    return (
      <div className='donation-page'>
        <Header />
        <div className={styles.content}>
          <div className={styles.main}>

            {this.state.banners? <Carousel>{(this.state.banners.map((b, i) => (
          <Link key={i} to="/" className={styles['banner']}>
            <img style={{width: '100%', height: '100%'}} src={b.img} alt="b"/>
          </Link>
            )))}</Carousel>: null}
            <RankShow />
            <div className={styles['project-content-wrap']}>
              <IndexShow />
            </div>
          </div>
          <Footer />
        </div>
      </div>
    )
  }
}
