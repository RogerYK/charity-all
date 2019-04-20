import React, { Component } from 'react'

import { Link } from 'react-router-dom'
import styles from './style.module.scss'
import { Divider, Row, Col, Pagination } from 'antd';
import ProjectCard from '../../../components/ProjectCard';
import { inject, observer } from 'mobx-react';


@inject('favoriteStore')
@observer
export default class FavorProject extends Component {

  render() {
    const {projects, total, setPage} = this.props.favoriteStore
    return (
      <div className={styles['favor-projects']}>
        <div className={styles['header']}>
          <span className={styles['title']}>我关注的项目</span>
          <span className={styles['total']}>共{total}个项目</span>
        </div>
        <Divider />
        <div className={styles['project-list']}>
          <div className={styles['content']}>
            {projects.length > 0 ?<Row gutter={24}>{projects.map((p, i) => (
              <Col className={styles['project-col']} key={i} span={8} style={{marginBottom: '20px'}} >
                <Link to="/detail">
                  <ProjectCard bordered={true} project={p} />
                </Link>
              </Col>
            ))}</Row>:<div className={styles['empty-projects']}>还没关注项目</div>}
          </div>
            <div className={styles['pagination-wrap']}>
              <Pagination 
                defaultCurrent={1}
                pageSize={9}
                total={total}
                onChange={(page) => setPage(page-1)}
                />
            </div>
        </div>
      </div>
    )
  }
}