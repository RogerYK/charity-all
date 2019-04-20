import React, { Component } from "react";
import {Skeleton } from "antd";


import { observer, inject } from "mobx-react";
import Introduction from "./BasicInfo/introduction";
import Detail from "./Detail/detail";


@inject('detailStore')
@observer
export default class DetailPage extends Component {

  componentDidMount() {
    console.log(this.props.match.params)
    const id = this.props.match.params.id;
    this.props.detailStore.id = id
    console.log(this.props.detailStore.id)
  }


  render() {
    const project = this.props.detailStore.project;

    return (
      <div className="content-container">
      {!project ? 
        <Skeleton />
        :
        <>
        <Introduction project={project} />
        <Detail project={project} />
        </>
      }
      </div>
    );
  }
}
