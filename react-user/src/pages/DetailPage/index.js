import React, { Component } from "react";
import { Skeleton, Modal, message } from "antd";


import { observer, inject } from "mobx-react";
import Introduction from "./BasicInfo/introduction";
import Detail from "./Detail/detail";
import { observable, action } from "mobx";
import DonationModal from "./DonationModal";
import api from "../../api";
import styles from "./style.module.scss"


@inject('detailStore', 'commonStore', 'userStore')
@observer
export default class DetailPage extends Component {

  @observable modalVisiable = false;

  componentDidMount() {
    const id = this.props.match.params.id;
    this.props.detailStore.id = id
  }

  @action
  handleCancel = () => {
    this.modalVisiable = false;
  }

  @action
  showModal = () => {
    this.modalVisiable = true;
  }

  handleDonate = () => {
    const logined = this.props.commonStore
    const history = this.props.history
    if (!logined) {
      history.push('/login')
      return
    }
    this.showModal()
  }

  @action
  handleFollow = () => {
    const {commonStore, history} = this.props
    if (!commonStore.logined) {
      setTimeout(() => {
        history.push('/login')
      }, 100);
      return
    }
    const project = this.props.detailStore.project;
    api.User.followProject({projectId: project.id, follow: true})
      .then(res => {
        project.followed = true
        this.props.detailStore.pullProject(project.id);
        message.success('关注成功')
      })
  }

  @action
  handleUnFollow = () => {
    const {commonStore, history} = this.props
    if (!commonStore.logined) {
      setTimeout(() => {
        history.push('/login')
      }, 100);
      return
    }
    const project = this.props.detailStore.project;
    api.User.followProject({projectId: project.id, follow: false})
      .then(res => {
        project.followed = false
        this.props.detailStore.pullProject(project.id);
        message.success('取消关注成功')
      })
  }


  render() {
    const curId = this.props.match.params.id;
    const project = this.props.detailStore.project;
    const {modalVisiable, handleCancel, handleDonate, handleFollow} = this

    return (
      <div className="content-container">
        <Skeleton 
          // loading={true}
          loading={!project || curId != project.id}
          className={styles.skeleton} 
          paragraph={{rows: 20}}
        >
          <Introduction project={project} onDonate={handleDonate}
            onFollow={handleFollow} 
            onUnFollow={this.handleUnFollow}
          />
        </Skeleton> 
        {!project || curId != project.id ? null :
        <>
        <Detail project={project} />
        <DonationModal
          visiable={modalVisiable}
          projectId={project.id}
          onCancel={handleCancel}
        />
        </>
        }
      </div>
    );
  }
}
        