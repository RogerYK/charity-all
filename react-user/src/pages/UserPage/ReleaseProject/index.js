
import React, { Component } from 'react'

import { Link } from 'react-router-dom'
import styles from './style.module.scss'
import 'react-quill/dist/quill.snow.css'; // ES6
import { Divider, Row, Col, Pagination, Modal } from 'antd';
import ProjectCard from '../../../components/ProjectCard';

import {getReleaseProjectsInfo, getReleaseProjects} from '@/api'
import AddProjectForm from './AddProjectForm';


export default class ReleaseProject extends Component {

  constructor(props) {
    super(props)
    this.state = {
      total: 0,
      projects: [],
      page: 1,
      modalVisible: false,
      newProject:{},
    }
  }

  componentDidMount() {
    getReleaseProjects(0).then(res => {
      this.setState({
        page: 1,
        total: res.data.total,
        projects: res.data.content,
      })
    })
  }

  showModal = () => {
    this.setState({modalVisible: true})
  }

  handleCancel = () => {
    this.setState({modalVisible: false})
  }


  render() {
    const total = this.state.total 
    const projects = this.state.projects
    const visible = this.state.modalVisible
    return (
      <div className={styles['favor-projects']}>
        <div className={styles['header']}>
          <span className={styles['title']}>我发布的项目</span>
          <span className={styles['total']}>共{total}个项目</span>
          <div onClick={e => this.showModal()} className={styles['add-project']}>发布项目</div>
        </div>
        <Divider />
        <div className={styles['project-list']}>
          <div className={styles['content']}>
            {projects.length > 0 ? <Row gutter={24}>{projects.map((p, i) => (
              <Col key={i} span={8} style={{marginBottom: '20px'}}>
                <Link to="/detail">
                  <ProjectCard bordered={true} project={p} />
                </Link>
              </Col>
            ))}</Row>:<div className={styles['empty-projects']}>
              还没有发布项目
            </div>}
          </div>
            <div className={styles['pagination-wrap']}>
              <Pagination defaultCurrent={1} current={this.state.page} pageSize={9} total={this.state.total} />
            </div>
        </div>

        <Modal
          width={800}
          name="新建项目"
          visible={visible}
          footer={null}
          onCancel={e => this.handleCancel()}
          title="添加项目"
        >
          <div className={styles['project-modal']}>
            <AddProjectForm onSuccess={this.handleCancel} />
          </div>
        </Modal>
      </div>
    )
  }
}