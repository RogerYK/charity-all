import React  from 'react'
import {Link} from 'react-router-dom'
import styles from './style.module.scss'
import ProjectList from '../../../components/ProjectList';
import IconFont from '../../../components/IconFont';

const RecommendProjects = props => {
  const projects = props.projects;

  return (
    <div className={styles["recommend-show"]}>
      <div className={styles["projects-wrap"]}>
        <div className={styles["project-list-wrap"]}>
          <div className={styles["title"]}>推荐项目
          <Link to="/explore" className={styles["link-more"]}>查看更多<IconFont type="icon-sanjiao3" /></Link></div>
          <div className={styles['project-list']}>
            <ProjectList projects={projects} cols={4}/>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RecommendProjects