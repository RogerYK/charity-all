
import React, { Component } from 'react'

import { Link } from 'react-router-dom'
import styles from './style.module.scss'
import 'react-quill/dist/quill.snow.css'; // ES6
import { Button, Pagination, Modal, Empty } from 'antd';
import ProjectCard from '../../../components/ProjectCard';

import { observer, inject } from 'mobx-react';
import { observable } from 'mobx';
import ProjectList from '../../../components/ProjectList';
import ScheduleForm from './AddScheduleForm';


@inject('releaseStore', 'userStore')
@observer
export default class ReleaseProject extends Component {

  @observable projectModalVisible = false

  @observable scheduleModalVisible = false

  @observable selectProjectId

  componentDidMount() {
    this.props.releaseStore.pullProjects()
  }

  showProjectModal = () => {
    this.projectModalVisible = true
  }

  projectModalCancel = () => {
    this.projectModalVisible = false
  }

  showScheduleModal = (projectId) => {
    this.selectProjectId = projectId
    this.scheduleModalVisible = true
  }

  scheduleModalCancel = () => {
    this.scheduleModalVisible = false
  }

  render() {
    const {total, projects, setPage} = this.props.releaseStore
    const {currentUser} = this.props.userStore
    const {
      selectProjectId,
      projectModalVisible,
      scheduleModalVisible,
      showProjectModal,
      projectModalCancel,
      showScheduleModal,
      scheduleModalCancel
    } = this
    return (
      <div className={styles['favor-projects']}>
        <div className="title">
          <span className={styles['title']}>我发布的项目</span>
          <span className={styles['total']}>共{total}个项目</span>
          { currentUser.identifyStatus === 'Identified' && <div onClick={showProjectModal} className={styles['add-project']}>发布项目</div>}
        </div>
        <div className={styles['project-list']}>
          <div className={styles['content']}>
            { projects.length > 0 ?
              <ProjectList 
                projects={projects}
                bordered={true}
                cols={3} 
                itemWidth={255}
                itemRender={p => (
                  <div key={p.id} className={styles['project-card-wrap']} >
                    <ProjectCard project={p} />
                    <div className={styles['project-mask']}>
                      <Button type="primary" style={{marginBottom: 20}} onClick={() => showScheduleModal(p.id)}>添加进展</Button>
                      <Link to={`/detail/${p.id}`}><Button>查看详情</Button></Link>
                    </div>
                  </div>
                )
                }
              />
              :
              <Empty description="未发布项目" />
            }
            
          </div>
          <div className={styles['pagination-wrap']}>
            { total > 9 ?
              <Pagination defaultCurrent={1} pageSize={9} total={this.state.total}
                onChange={(page) => setPage(page)}
              />
              :
              null
            }
          </div>
        </div>

        <Modal
          width={500}
          name="添加进展"
          visible={scheduleModalVisible}
          footer={null}
          onCancel={scheduleModalCancel}
          title="添加进展"
        >
          <ScheduleForm onCancel={scheduleModalCancel} projectId={selectProjectId} />
        </Modal>

        <Modal
          width={800}
          name="新建项目"
          visible={projectModalVisible}
          footer={null}
          onCancel={projectModalCancel}
          title="添加项目"
        >
          <div className={styles['project-modal']}>
            {/* <AddProjectForm onCancel={projectModalCancel} /> */}
          </div>
        </Modal>
      </div>
    )
  }
}