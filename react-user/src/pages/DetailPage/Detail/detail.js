import React from "react";
import { Tabs, Timeline} from "antd";

import styles from './detail.module.scss'
import  CommentList from "./CommentList";
const TabPane = Tabs.TabPane;

const Detail = ({project}) => {
  return (
    <div  className={styles["content-bottom"]}>
      <div className={styles["bottom-left"]}>
        <Tabs defaultActiveKey="1">
          <TabPane className={styles["project-content"]} tab="捐助说明" key="1">
          <div dangerouslySetInnerHTML={{__html: project.content}} />
          </TabPane>
          <TabPane className={styles["project-timeline"]} tab="项目进展" key="2">
            <Timeline className={styles["timeline"]}>
            {project.projectSchedules.map(s => (
              <Timeline.Item className={styles["p-t-item"]} >
              <div className={styles['timeline-right']}>
                <div 
                  dangerouslySetInnerHTML={{__html: s.content}}
                />
                <div className={styles['time']}>{s.createdTime}</div>
              </div>
              </Timeline.Item>
            ))}
            </Timeline>
          </TabPane>
          <TabPane className={styles['project-comments']} tab="评论" key="3">
            <CommentList  projectId={project.id} />
          </TabPane>
        </Tabs>
      </div>
      <div className={styles["bottom-right"]}>
        <div className={styles["right-recommend"]}>
          <div className={styles["title"]}>
            <span className={styles["text"]}>你可能会关注</span>
          </div>
          <div className={styles["project-list"]} />
        </div>
        <div className={styles["frequently-problems"]}>
          <div className={styles["title"]}>
            <span className={styles["text"]}>常见问题</span>
            <a href=".">意见反馈</a>
          </div>
          <div className={styles["problem-list"]}>
            <div className={styles["item"]}>
              <a href=".">1. 乐捐平台项目审核标准是什么？</a>
            </div>
            <div className={styles["item"]}>
              <a href=".">2. 哪些用户可以在乐捐平台上发起项目？</a>
            </div>
            <div className={styles["item"]}>
              <a href=".">3. 如何在乐捐平台上发起项目？</a>
            </div>
            <div className={styles["item"]}>
              <a href=".">4. 完成项目募捐后，如何进行拨款？</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Detail