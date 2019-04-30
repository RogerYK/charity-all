import React, { Component } from 'react'

import styles from './style.module.scss'
import { Input, Button } from 'antd';
import { observer, inject } from 'mobx-react';
import { observable } from 'mobx';

@inject('searchStore')
@observer
export default class SearchDiv extends Component {

  @observable searchText = ''

  handleChange = (e) => {
    e.preventDefault()
    this.searchText = e.target.value
  }

  handleClick = () => {
    console.log('click')
    this.props.searchStore.setSearchText(this.searchText)
  }


  render() {
    return (
      <div className={styles['search-div']}>
        <Input size="large" className={styles['search-input']} value={this.searchText} onChange={this.handleChange} />
        <Button size="large" className={styles['search-btn']} onClick={this.handleClick}>搜索</Button>
      </div>
    )
  }
}
