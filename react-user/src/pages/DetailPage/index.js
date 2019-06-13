import React, { Component } from "react";
import { Skeleton, Modal } from "antd";


import { observer, inject } from "mobx-react";
import Introduction from "./BasicInfo/introduction";
import Detail from "./Detail/detail";
import { observable, action } from "mobx";
import DonationModal from "./DonationModal";


@inject('detailStore', 'commonStore')
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


  render() {
    const project = this.props.detailStore.project;
    const {modalVisiable, handleCancel, handleDonate} = this

    return (
      <div className="content-container">
      {!project ? 
        <Skeleton />
        :
        <>
        <Introduction project={project} onDonate={handleDonate} />
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
