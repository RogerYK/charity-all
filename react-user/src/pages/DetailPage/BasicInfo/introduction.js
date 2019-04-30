import React from "react";

import styles from "./introduction.module.scss";
import Progress from "../../../components/Progress";
import {convertMoneyStr} from "../../../utils/format";
import moment from "moment";
import { Button } from "antd";
import IconFont from "../../../components/IconFont";

const Introduction = props => {
  const project = props.project;
  const author = project.author;
  const category = project.category;

  const raisedMoney = project.raisedMoney;
  const targetMoney = project.targetMoney;
  const percent = (raisedMoney / targetMoney) * 100;
  const [raisedStr, targetStr] = [raisedMoney, targetMoney].map(
    convertMoneyStr
  );
  const timeConent = getTimeCoutent(project);
  return (
    <div className={styles["container-wrap"]}>
      <div className={styles["container"]}>
        <div className={styles["left"]}>
          <div className={styles["title"]}>{project.name}</div>
          <img src={project.img} alt="img" />
          <div className={styles["desc"]}><div>{project.summary}</div></div>
        </div>
        <div className={styles["right"]}>
          <div className={styles['top-wrap']}>
          <div className={styles["author"]}>
            <div className={styles["text"]}>
              <div className={styles["name"]}>{author.nickName}</div>
              <div className={styles["desc"]} > 发起了该项目 </div>
              <div className={styles['authen']}><IconFont className={styles['icon']} type="icon-renzheng" />个人认证</div>
            </div>
            <div className={styles["avatar"]}>
              <img src={author.avatar} alt="avatar" />
            </div>
          </div>
          <div className={styles["types"]}>
            <div className={styles["category"]}><IconFont className={styles['icon']} type="icon-fenlei" />{category.name}</div>
          </div>

          </div>
          <div className={styles["center-wrap"]}>
            <div className={styles["progress"]}>
              <div className={styles["raised-money"]}>已筹￥{raisedStr}</div>
              <Progress value={percent} />
              <div className={styles["bottom"]}>
                <div className={styles["target-money"]}>
                  目标金额￥{targetStr}
                </div>
                <div className={styles["percent"]}>{percent.toFixed(2)}%</div>
              </div>
            </div>
            <div className={styles["item"]}>
              <div className={styles["field"]}>{timeConent.time}</div>
              <div className={styles["label"]}>{timeConent.label}</div>
            </div>
            <div className={styles["item"]}>
              <div className={styles["field"]}>{project.donorCount}人</div>
              <div className={styles["label"]}>支持人数</div>
            </div>
            <div className={styles["action"]}>
              <Button className={styles['btn']} size="large" type="primary">立即支持</Button>
              <div className={styles["follow"]}><IconFont className={styles['icon']} type="icon-31shoucang" />关注</div>
            </div>
            <div className={styles['info']}> 人人都有一颗心,大多是善良的。在别人有难的时候,我们献出一点爱心,也许就能挽救一条生命。 </div>
          </div>
          <div className={styles["share-links"]}>
            <div className={styles["text"]}>分享项目到</div>
            <div className={styles["links"]}>
              <IconFont className={styles['icon']} type="icon-xinlang" />
              <IconFont className={styles['icon']} type="icon-pengyouquan" />
              <IconFont className={styles['icon']} type="icon-qq-copy" />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

function getTimeCoutent(project) {
  const startTime = moment(project.startTime, "YYYY-MM-DD HH:mm:ss");
  const endTime = moment(project.endTime, "YYYY-MM-DD HH:mm:ss");
  const days = endTime.subtract(startTime).days();
  const now = moment();
  const timeConent = {};
  if (now.isAfter(startTime)) {
    timeConent.time = `${startTime.format("YYYY-MM-DD")}`;
    timeConent.label = "开始时间";
  } else if (now.isBefore(endTime)) {
    timeConent.time = `${days}天`;
    timeConent.label = "剩余时间";
  } else {
    timeConent.time = `${endTime.format("YYYY-MM-DD")}天`;
    timeConent.label = "结束时间";
  }
  return timeConent;
}

export default Introduction;
