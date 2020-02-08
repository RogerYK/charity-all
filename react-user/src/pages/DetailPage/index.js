import React, { Component } from "react";
import { Skeleton, Modal, message } from "antd";


import { observer, inject } from "mobx-react";
import Introduction from "./BasicInfo/introduction";
import Detail from "./Detail/detail";
import { observable, action } from "mobx";
import DonationModal from "./DonationModal";
import api from "../../api";


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
    const project = this.props.detailStore.project;
    api.User.followProject({projectId: project.id})
      .then(res => {
        project.followed = true
        this.props.detailStore.pullProject(project.id);
        message.success('关注成功')
      })
  }


  render() {
    const project = this.props.detailStore.project;
    const {modalVisiable, handleCancel, handleDonate, handleFollow} = this

    return (
      <div className="content-container">
        {!project ? 
          <Skeleton /> :
        <>
        <Introduction project={project} onDonate={handleDonate}
          onFollow={handleFollow} />
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
        