import React, { Component } from 'react'
import { Link } from 'react-router-dom'

import Carousel from '../../components/Carousel'
import styles from './style.module.scss'


import RankShow from './RankShow';
import { inject, observer } from 'mobx-react';
import RecommendProjects from './RecommendProjects';

@inject('indexStore')
@observer
export default class DonationPage extends Component {

  componentDidMount() {
    this.props.indexStore.pullData()
  }

  render() {
    const {banners, hotProjects, hotNews, recommendProjects} = this.props.indexStore
    console.log(this.props.indexStore)

    return (
      <div>
            {banners? <Banner banners={banners} />: null}
            <RankShow hotProjects={hotProjects} hotNews={hotNews} />
            <RecommendProjects projects={recommendProjects} />
      </div>
    )
  }
}

function Banner(props) {
  const banners = props.banners

  return (
  <Carousel>
    {banners.map(b => 
      <Link key="b.id" to="/" className={styles.banner}>
        <img src={b.img} alt="banner" />
      </Link>
    )}
  </Carousel>
  )
}
