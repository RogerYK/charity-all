import React from "react";

import styles from "./style.module.scss";
import { List } from "antd";
import {Link} from 'react-router-dom'
import moment from "moment";
import IconFont from "../../../components/IconFont";

const LatestNews = ({ newsList }) => {
  return (
    <div className={styles["latest-news-wrap"]}>
      <div className={styles["latest-news"]}>
        <div className={styles["left"]}>
          <div className="title">最新资讯</div>
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
                  <img className={styles['img']} src={news.img} alt="img" />
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
        </div>
      </div>
    </div>
  );
};

export default LatestNews