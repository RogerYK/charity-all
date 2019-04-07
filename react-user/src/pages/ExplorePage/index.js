import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { Menu, Dropdown, Icon, Row, Col, Pagination } from 'antd'

import styles from './style.module.scss'
import Header from '../../components/Header';
import Footer from '../../components/Footer';
import ProjectCard from '../../components/ProjectCard';
import { getAllCategories, getProjectsByCategory } from '../../api';



export default class ExplorePage extends Component {

  constructor(props) {
    super(props)

    this.state = {
      page: 0,
      total: 0,
      projects: [],
      categories: [],
      curCategoryId: undefined,
    }
  }

  componentDidMount() {
    getAllCategories().then(res => {
      this.setState({categories: res.data, curCategoryId: res.data[0].id},
        this.getProjects)
    })
  }

  getProjects = () => {
    getProjectsByCategory(this.state.curCategoryId, this.state.page, 12).then(res => {
      this.setState({total: res.data.total, projects: res.data.content})
    })
  }

  setPage = (page) => {
    this.setState({page: page-1}, this.getProjects)
  }

  setCurCategoryId = ({key}) => {
    this.setState({curCategoryId:key}, this.getProjects)
  }



  categoryMenu() {
    return (
      <Menu onClick={this.setCurCategoryId}>
        {this.state.categories.map(c => <Menu.Item key={c.id}>{c.name}</Menu.Item>)}
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
    return (
      <div className={styles['expore-page']}>
        <Header />
        <div className={styles['main']}>
          <div className={styles['header']}>
            <div>
              <span className={styles['title']}>发现项目</span>
              <span>共{this.state.total}个项目</span>
            </div>
            <div>
              <Dropdown className={styles['dropdown']} overlay={this.categoryMenu()}><span>全部分类</span></Dropdown>
              <Dropdown className={styles['dropdown']} overlay={this.stateMenu()}><span>全部</span></Dropdown>
              <Dropdown className={styles['dropdown']} overlay={this.sortMenu()}><span>全部</span></Dropdown>
            </div>
          </div>
          <div className={styles['content']}>
            <Row gutter={24}>{this.state.projects.map((p, i) => (
              <Col key={i}  span={6} style={{ marginBottom: '20px' }}>
                <Link to={"/detail/"+p.id}>
                  <ProjectCard project={p} />
                </Link>
              </Col>
            ))}</Row>
            <div className={styles['pagination-wrap']}>
              <Pagination 
                current={this.state.page+1} 
                pageSize={12} 
                onChange={this.setPage}
                total={this.state.total} />
            </div>
          </div>
        </div>
        <Footer />
      </div>
    )
  }
}