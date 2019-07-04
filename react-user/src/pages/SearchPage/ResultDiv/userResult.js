import React, { Component } from 'react'
import {Link} from 'react-router-dom'
import { List, Button, Avatar, Pagination } from 'antd';
import { inject, observer } from 'mobx-react';


@inject('searchStore')
@observer
export default class UserResult extends Component {

  render() {
    const searchStore = this.props.searchStore
    const userResult = searchStore.userResult
    const {users, page, total, setPage} = userResult
    return (
      <>
        <List 
          dataSource={users}
          renderItem={(user) => (
            <List.Item actions={[<Button type="primary">关注</Button>]}>
              <Link to={`/user/detail/${user.id}`}>
              <List.Item.Meta
                avatar={<Avatar src={user.avatar} />}
                title={user.nickName}
                description={user.presentation}
              ></List.Item.Meta>
              </Link>
            </List.Item>
          )}
        />
        <Pagination className="child-center" current={page+1} pageSize={12}
          onChange={(page) => setPage(page-1)}
          total={total}
         />
      </>
    )
  }
}