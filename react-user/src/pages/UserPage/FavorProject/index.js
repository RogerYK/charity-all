import React, { Component } from 'react'

import styles from './style.module.scss'
import { Pagination, Empty } from 'antd';
import { inject, observer } from 'mobx-react';
import ProjectList from '../../../components/ProjectList';


@inject('favoriteStore')
@observer
export default class FavorProject extends Component {

  componentDidMount() {
    const favoriteStore = this.props.favoriteStore
    favoriteStore.firstPull();
  }

  render() {
    const favoriteStore = this.props.favoriteStore
    const {projects, total, setPage} = favoriteStore
    console.log(projects)
    return (
      <div className={styles['favor-projects']}>
        <div className="title">
          <span className={styles['title']}>我关注的项目</span>
          <span className={styles['total']}>共{total}个项目</span>
        </div>
        <div className={styles['project-list']}>
          <div className={styles['content']}>
            {projects.length > 0 ?
              <ProjectList projects={projects} bordered={true} cols={3} itemWidth={'255px'} />
              :
              <Empty description={<span>还没关注任何项目</span>} />
            }
          </div>
          {total > 9 ? <Pagination 
            className={styles['pagination']}
            defaultCurrent={1}
            pageSize={9}
            total={total}
            onChange={(page) => setPage(page-1)}
          /> : null}
        </div>
      </div>
    )
  }
}