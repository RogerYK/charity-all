import React, {Component} from 'react'


import styles from './user.module.scss'
import { Menu, Avatar, Spin } from 'antd';
import {Switch, Route, Link} from 'react-router-dom'
import IndexContent from './index/index_content';
import FavorProject from './FavorProject'

import ReleaseProject from './ReleaseProject';
import DonationRecord from './DonationRecord';
import UserInfo from './UserInfo';
import FeedBack from './Feedback';
import Authentication from './Authentication';
import { observer, inject } from 'mobx-react';
import ReleaseNews from './ReleaseNews';

@inject("userStore", "commonStore")
@observer
export default class UserPage extends Component {

  render() {
    const logined = this.props.commonStore.logined
    if (!logined) {
      this.props.history.push('/login')
    }
    const {currentUser, pulling} = this.props.userStore
    return (
      <div className={styles['container-wrap']}>
      <div className={styles["container"]}>
        <Menu className={styles["side-menu"]} defaultOpenKeys={["index"]}>
          <Menu.Item key="index" className={styles["index"]}>
            <Link to="/user">
              {pulling || !logined ?
                <>
                  <Avatar icon="user" style={{marginRight: "20px"}} />
                  <span>加载中</span>
                </>
                :
                <>
                  <Avatar src={currentUser.avatar} style={{ marginRight: "20px" }} />
                  <span>{currentUser.nickname}</span>
                </>

              }
            </Link>
          </Menu.Item>
          <Menu.ItemGroup
            title={<span className={styles["group-title"]}>项目管理</span>}
          >
            <Menu.Item key="watch-projects">
              <Link to="/user/project/favor">关注的项目</Link>
            </Menu.Item>
            <Menu.Item key="my-projects">
              <Link to="/user/project/release">发起的项目</Link>
            </Menu.Item>
          </Menu.ItemGroup>
          <Menu.ItemGroup
            title={<span className={styles["group-title"]}>捐款信息</span>}
          >
            <Menu.Item key="donation-reocrd">
              <Link to="/user/record/donation">捐款记录</Link>
            </Menu.Item>
          </Menu.ItemGroup>
          <Menu.ItemGroup
            title={<span className={styles['group-title']}>资讯管理</span>}
          >
            <Menu.Item key="news-release">
              <Link to="/user/news/release">发布的资讯</Link>
            </Menu.Item>
          </Menu.ItemGroup>
          <Menu.ItemGroup
            title={<span className={styles["group-title"]}>我的设置</span>}
          >
            <Menu.Item key="user-info">
              <Link to="/user/userinfo">个人资料</Link>
            </Menu.Item>
            <Menu.Item key="authtication">
              <Link to="/user/authentication" />账号认证
            </Menu.Item>
          </Menu.ItemGroup>
          <Menu.ItemGroup
            title={<span className={styles["group-title"]}>客服中心</span>}
          >
            <Menu.Item key="liuyan">
              <Link to="/user/feedback">我的留言</Link>
            </Menu.Item>
          </Menu.ItemGroup>
        </Menu>
        <div className={styles["content"]}>
        { pulling || !logined ?
          <div className={styles['spin-wrap']}><Spin size="large" /></div>
          :
          <Switch>
            <Route exact path="/user/project/favor" component={FavorProject} />
            <Route
              exact
              path="/user/project/release"
              component={ReleaseProject}
            />
            <Route
              exact
              path="/user/record/donation"
              component={DonationRecord}
            />
            <Route exact path="/user/userinfo" component={UserInfo} />
            <Route exact path="/user/feedback" component={FeedBack} />
            <Route
              exact
              path="/user/authentication"
              component={Authentication}
            />
            <Route path="/user/news/release" component={ReleaseNews} />
            <Route component={IndexContent} />
          </Switch>
        }
        </div>
      </div>
      </div>
    )
  }
}
