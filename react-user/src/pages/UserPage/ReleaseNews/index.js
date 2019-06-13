import React, { Component } from 'react'
import { List, Pagination, Modal } from 'antd';
import { inject, observer } from 'mobx-react';
import ReleaseNewsStore from './store';
import styles from './style.module.scss'
import { observable, action } from 'mobx';
import AddNewsForm from './AddNews';

@inject('userStore')
@observer
export default class ReleaseNews extends Component {

  @observable modalVisible = false

  @action
  hideModal = () => {
    this.modalVisible = false
  }

  @action
  showModal = () => {
    this.modalVisible = true
  }

  constructor(props) {
    super(props)
    const userId = props.userStore.currentUser.id
    this.newsStore = new ReleaseNewsStore(userId)
  }


  render() {
    const {
      news,
      page,
      total,
      setPage,
    } = this.newsStore
    return (
      <div>
        <div className="title">
          已发布资讯
          <span className={styles['total']}>共{total}个</span>
          <span onClick={this.showModal} className={styles['add-btn']}>发布资讯</span>
        </div>
        <div className={styles['content']}>
          <List 
            dataSource={news}
            itemLayout="horizontal"
            renderItem={n => (
              <List.Item
                actions={[<a href="/">查看</a>]}
              >
                <List.Item.Meta 
                  title={n.title}
                  description={n.introduce}
                />
              </List.Item>
            )}
          />
          {total > 9 ? <Pagination 
            className={styles['pagination']}
            current={page}
            pageSize={9}
            total={total}
            onChange={page => setPage(page-1)}
          /> : null}
        </div>

        <Modal
          width={800}
          name="发布新闻"
          visible={this.modalVisible}
          footer={null}
          onCancel={this.hideModal}
          title="发布新闻"
        >
          <AddNewsForm onCancel={this.hideModal} />
        </Modal>
      </div>
    )
  }
}