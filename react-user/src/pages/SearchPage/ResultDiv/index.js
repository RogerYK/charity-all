import React, {Component} from 'react'
import { observer, inject } from 'mobx-react';
import ProjectList from '../../../components/ProjectList';
import styles from './style.module.scss'
import { Pagination, Empty } from 'antd';

@inject('searchStore')
@observer
export class ProjectResult extends Component {

  render() {
    const {total, projects, setPage} = this.props.searchStore.projectResult 

    return (
      <>
      { total === 0 ?
        <Empty description={<span>没有搜索到项目</span>} />
        :
        <>
        <ProjectList bordered={true} projects={projects} cols={4} />
        <div className={styles['pagination-wrap']}>
          <Pagination defaultCurrent={1} pageSize={12}
            onChange={(page) => setPage(page-1)}
            total={total}
           />
        </div>
        </>
      }
      </>
    )

  }
}