import React, { Component } from 'react'
import { Menu, Dropdown, Pagination } from 'antd'

import styles from './style.module.scss'
import { observer, inject } from 'mobx-react';
import ProjectList from '../../components/ProjectList';



@inject('exploreStore')
@observer
export default class ExplorePage extends Component {

  componentDidMount() {
    this.props.exploreStore.pullCategories()
  }

  categoryMenu() {
    const categories = this.props.exploreStore.categories
    return (
      <Menu onClick={this.setCurCategoryId}>
        {categories.map(c => <Menu.Item key={c.id}>{c.name}</Menu.Item>)}
      </Menu>
    )
  }

  stateMenu() {
    return (
      <Menu>
        <Menu.Item>全部</Menu.Item>
        <Menu.Item>募捐中</Menu.Item>
        <Menu.Item>执行</Menu.Item>
        <Menu.Item>结束</Menu.Item>
      </Menu>
    )
  }

  sortMenu() {
    return (
      <Menu>
        <Menu.Item>最新</Menu.Item>
        <Menu.Item>最热</Menu.Item>
      </Menu>
    )
  }

  render() {
    const store = this.props.exploreStore
    const {total, page, projects} = store
    return (
      <div className={styles['main-wrap']}>

        <div className={styles['main']}>
          <div className={styles['header']}>
            <div>
              <span className={styles['title']}>发现项目</span>
              <span>共{total}个项目</span>
            </div>
            <div>
              <Dropdown className={styles['dropdown']} overlay={this.categoryMenu()}><span>全部分类</span></Dropdown>
              <Dropdown className={styles['dropdown']} overlay={this.stateMenu()}><span>全部</span></Dropdown>
              <Dropdown className={styles['dropdown']} overlay={this.sortMenu()}><span>全部</span></Dropdown>
            </div>
          </div>
          <div className={styles['content']}>
            <ProjectList cols={4} projects={projects} />
            <div className={styles['pagination-wrap']}>
              <Pagination 
                current={page+1} 
                pageSize={12} 
                onChange={(p) => store.setPage(p-1)}
                total={total} />
            </div>
          </div>
        </div>
      </div>
    )
  }
}