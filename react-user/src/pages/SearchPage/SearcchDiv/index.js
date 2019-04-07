import React, { Component } from "react";

import { Input, Select } from "antd";

import "./style.scss";

const Search = Input.Search;
const Option = Select.Option;

export const SearchDiv = props => {
  return (
    <div className="search-div">
      <div className="project-fliter">
        <div className="label">项目状态</div>
        <Select defaultValue="runing">
          <Option value="ruging">募捐中</Option>
          <Option value="executing">执行中</Option>
          <Option value="ending">已结束</Option>
        </Select>
      </div>
      <div className="project-fliter">
        <div className="label">项目领域</div>
        <Select defaultValue="all">
          <Option value="all">全部分类</Option>
          <Option value="sick">疾病救助</Option>
          <Option value="disaster">扶贫/救灾</Option>
          <Option value="educate">教育/助学</Option>
          <Option value="protection">环保/动物保护</Option>
          <Option value="others">其他</Option>
        </Select>
      </div>
      <Search onSearch={props.onSearch} className="searchInput" placeholder="搜索项目" />
    </div>
  );
};
