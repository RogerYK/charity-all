import React, { Component } from "react";

import styles from "./style.module.scss";
import { Input, Button } from "antd";
import { observer, inject } from "mobx-react";
import { observable } from "mobx";
import { withRouter } from "react-router-dom";

@inject("searchStore")
@observer
class SearchDiv extends Component {
  @observable searchText = "";

  componentDidMount() {
    const { location } = this.props;
    const params = {};
    const searchText = decodeURIComponent(location.search.slice(1));
    searchText
      .split("&")
      .map(text => text.split("="))
      .forEach(pair => {
        params[pair[0]] = pair[1];
      });

    if (params["search"]) {
      this.searchText = params["search"];
      this.props.searchStore.setSearchText(this.searchText);
    }
  }

  handleChange = e => {
    e.preventDefault();
    this.searchText = e.target.value;
  };

  handleClick = () => {
    console.log("click");
    this.props.searchStore.setSearchText(this.searchText);
  };

  render() {
    return (
      <div className={styles["search-div"]}>
        <Input
          size="large"
          className={styles["search-input"]}
          value={this.searchText}
          onChange={this.handleChange}
        />
        <Button
          size="large"
          className={styles["search-btn"]}
          onClick={this.handleClick}
        >
          搜索
        </Button>
      </div>
    );
  }
}

export default withRouter(SearchDiv);
