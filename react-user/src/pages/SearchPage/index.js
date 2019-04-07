import React, { Component } from 'react'

import Header from '../../components/Header'
import Footer from '../../components/Footer';

import styles from './style.module.scss'
import { SearchDiv } from './SearcchDiv';
import { ProjectList } from './ProjectList';
import { searchProjectByName } from '../../api';
import { Pagination } from 'antd';

export default class SearchPage extends Component {

  state = {
    name: '',
    projects: [],
    page: 0,
    total: 0,
  }

  componentDidMount() {
    window.scrollTo(0, 0)
  }

  handleSearch= (name) => {
    this.setState({name}, this.getProjects)
  }

  getProjects = () => {
    searchProjectByName(this.state.name, this.state.page).then(res => {
      this.setState({total: res.data.total, projects: res.data.content})
    })
  }

  render() {
    return (
      <div className='search-page'>
        <Header />
        <SearchDiv onSearch={this.handleSearch}/>
        <ProjectList projects={this.state.projects}/>
        <div className={styles['pagination-wrap']}>
          <Pagination current={this.state.page+1} pageSize={10} total={this.state.total} />
        </div>
        <Footer />
      </div>
    )
  }
}