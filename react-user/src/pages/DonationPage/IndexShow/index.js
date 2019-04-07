import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { Row, Col, Card, Avatar, Skeleton } from 'antd'

import {getRecommendProjects, getRecommendBanners} from '../../../api'

import styles from './index.module.scss'
import ProjectCard from '../../../components/ProjectCard';

export default class IndexShow extends Component {

  constructor(props) {
    super(props)
    this.state = {projects: new Array(4).fill(null)}
  }

  componentDidMount(){
    getRecommendProjects().then(res => {
      this.setState({projects: res.data})
    })
    
  }

  getProjects() {
    return (
      <Row className={styles['project-list']} gutter={24}>
        {this.state.projects.map((p, i) => (
          !p ? <Skeleton key={i} loading={true} /> :
            <Col key={i} span={6} style={{ marginBottom: 20 }} >
              <Link to={`/detail/${p.id}`} key={i}>
                <ProjectCard project={p} />
              </Link>
            </Col>
        ))}
      </Row>
    )
  }

  render() {
    return (
      <div className={styles['recommend-show']}>
        <div className={styles['projects-wrap']}>
          <div className={styles['project-list-wrap']}>
            <div className={styles['title']}>推荐项目</div>
            {this.getProjects()}
          </div>
        </div>
      </div>
    )
  }
}