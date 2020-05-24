import React, {Component} from 'react'
import {Link} from 'react-router-dom'
import { observer, inject } from 'mobx-react';
import ProjectList from '../../../components/ProjectList';
import styles from './style.module.scss'
import { Pagination, Empty, List } from 'antd';
import moment from "moment";
import IconFont from "../../../components/IconFont";

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
        <ProjectList bordered={true} projects={projects} cols={4} itemWidth={255}/>
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

@inject('searchStore')
@observer
export class NewsResult extends Component {

  render() {
    const searchStore = this.props.searchStore
    const newsResult = searchStore.newsResult
    const {newsList, total, setPage} = newsResult

    return (
      <>
          <List
            className={styles['news-list']}
            dataSource={newsList}
            renderItem={news => (
              <div className={styles["news"]}>
                <div className={styles["author"]}>
                  <img
                    src={news.author.avatar}
                    className={styles["avatar"]}
                    alt="avatar"
                  />
                  <div className={styles["text"]}>
                    <div className={styles["name"]}>{news.author.nickName}</div>
                    <div className={styles["time"]}>
                      {moment(
                        news.createdTime,
                        "YYYY-MM-DD HH:mm:ss"
                      ).fromNow()}
                      发布
                    </div>
                  </div>
                </div>
                <div className={styles["content"]}>
                  <div className={styles["introduce"]} >
                    {news.introduce}
                    <Link to={`/news/detail/${news.id}`}>查看更多</Link>
                  </div>
                  <div className={styles["action-list"]}>
                    <span className={styles["action"]}>
                      <IconFont className={styles['icon']} type="icon-pinglun" />
                      评论
                    </span>
                    <span className={styles["action"]}>
                      <IconFont className={styles['icon']} type="icon-zan" />赞
                    </span>
                  </div>
                </div>
              </div>
            )}
          />
        <div className={styles['pagination-wrap']}>
          <Pagination defaultCurrent={1} pageSize={12}
            onChange={(page) => setPage(page-1)}
            total={total}
          />
        </div>
      </>
    )
  }
}