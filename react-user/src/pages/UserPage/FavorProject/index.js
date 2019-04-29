import React, { Component } from 'react'

import { Link } from 'react-router-dom'
import styles from './style.module.scss'
import { Divider, Row, Col, Pagination, Empty } from 'antd';
import ProjectCard from '../../../components/ProjectCard';
import { inject, observer } from 'mobx-react';
import ProjectList from '../../../components/ProjectList';


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
            {projects.length > 0 ?
              <ProjectList projects={projects} bordered={true} cols={3} />
            :
            <Empty description={<span>还没关注任何项目</span>} />
            }
          </div>
            <div className={styles['pagination-wrap']}>
              {total > 9 ? <Pagination 
                defaultCurrent={1}
                pageSize={9}
                total={total}
                onChange={(page) => setPage(page-1)}
                /> : null}
            </div>
        </div>
      </div>
    )
  }
}