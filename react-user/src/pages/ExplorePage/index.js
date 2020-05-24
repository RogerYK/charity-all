import React, { Component } from 'react'
import { Menu, Dropdown, Pagination } from 'antd'

import styles from './style.module.scss'
import { observer, inject } from 'mobx-react';
import ProjectList from '../../components/ProjectList';


const sortLabels = {
  'createdTime': '最新',
  'watchCount': '最热',
}


@inject('exploreStore')
@observer
export default class ExplorePage extends Component {

  componentDidMount() {
    this.props.exploreStore.pullCategories()
  }

  categoryMenu() {
    let {categories, setCurCategoryId} = this.props.exploreStore
    categories = [{id: 'all', name: '全部分类'}, ...categories]
    return (
      <Menu onClick={({key}) => {
        if (key !== 'all') {
          setCurCategoryId(key)
        } else {
          setCurCategoryId(null)
        }
      }}>
        {categories.map(c => <Menu.Item key={c.id}>{c.name}</Menu.Item>)}
      </Menu>
    )
  }

  categoryLable() {
    const { categories, curCategoryId } = this.props.exploreStore
    console.log(categories, curCategoryId)
    for (const c of categories) {
      if (c.id === Number(curCategoryId)) {
        return c.name
      }
    }
    return '全部分类'
  }

  sortMenu() {
    const exploreStore = this.props.exploreStore
    return (
      <Menu onClick={({key}) => {
        exploreStore.setField(key)
      }}>
        <Menu.Item key='createdTime'>最新</Menu.Item>
        <Menu.Item key='watchCount'>最热</Menu.Item>
      </Menu>
    )
  }

  render() {
    const store = this.props.exploreStore
    const {total, page, projects, field} = store
    return (
      <div className={styles['main-wrap']}>

        <div className={styles['main']}>
          <div className={styles['header']}>
            <div>
              <span className={styles['title']}>发现项目</span>
              <span>共{total}个项目</span>
            </div>
            <div>
              <Dropdown className={styles['dropdown']} overlay={this.categoryMenu()}><span>{this.categoryLable()}</span></Dropdown>
              <Dropdown className={styles['dropdown']} overlay={this.sortMenu()}><span>{sortLabels[field]}</span></Dropdown>
            </div>
          </div>
          <div className={styles['content']}>
            <ProjectList itemWidth={255} cols={4} projects={projects} />
            <div className={styles['pagination-wrap']}>
              <Pagination 
                current={page} 
                pageSize={12} 
                onChange={(p) => store.setPage(p)}
                total={total} />
            </div>
          </div>
        </div>
      </div>
    )
  }
}