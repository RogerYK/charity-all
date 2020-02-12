import React, { Component } from 'react'
import ReactQuill from 'react-quill'
import { UploadOutlined } from '@ant-design/icons';
import { Input, Upload, Button, DatePicker, InputNumber, message, Select, Form } from 'antd';
import config from '../../../../utils/config';
import { inject, observer } from 'mobx-react';
import api from '../../../../api';

const TimeFormat = 'YYYY-MM-DD HH:mm:ss'

const normFile = e => {
  return e && e.fileList
}

@inject('releaseStore')
@observer
export default class AddProjectForm extends Component {

  constructor(props) {
    super(props)
    this.state = {
      categories: []
    }
  }

  componentDidMount() {
    api.Category.all()
      .then(res => {
        this.setState({categories: res.data})
      }).catch(res => {
        message.error('获取分类出错')
      })
  }

  handleSubmit = (values) => {
    console.log('project submit', values)
    const {
      name,
      categoryId,
      targetMoney,
      content,
      timeRange,
      imgList,
      galleryList,
    } = values

    const data = {
      id: this.props.id,
      name,
      categoryId,
      targetMoney,
      content,
      startTime: timeRange[0].format(TimeFormat),
      endTime: timeRange[1].format(TimeFormat),
      img: imgList.map(f => f.response.data)[0],
      gallery: galleryList.map(f => f.response.data),
    }
    this.props.releaseStore.saveProject(data)
      .then(res => {
        message.success('保存成功')
        this.props.onCancel()
      }).catch(res => {
        message.error('保存失败')
        this.props.onCancel()
      })
  }

  render() {
    const Item = Form.Item
    const formItemLayout = {labelCol: {span: 4}, wrapperCol: {span: 20}}
    const tailItemLayout = {wrapperCol: {span: 20, offset: 4}}
    return (
      <Form initialValues={{content: ''}} onFinish={this.handleSubmit}>
        <Item name="name" {...formItemLayout} label="项目名称">
          <Input />
        </Item>
        <Item valuePropName="fileList" getValueFromEvent={normFile} name="imgList" {...formItemLayout} label="项目封面" extra="用于在列表卡片中显示">
          <Upload
            action={`${config.baseURL}/upload/`}
            multiple={false}
          >
            <Button><UploadOutlined /> 点击上传图片</Button>
          </Upload>
        </Item>
        <Item valuePropName="fileList" getValueFromEvent={normFile} name="galleryList" {...formItemLayout} label="项目图片">
          <Upload
            action={`${config.baseURL}/upload/`}
          >
            <Button>
              <UploadOutlined /> 点击上传图片
            </Button>
          </Upload>
        </Item>
        <Item name="categoryId" {...formItemLayout} label="类别">
          <Select>
            {this.state.categories.map(c => (
              <Select.Option value={c.id} key={c.id}>
                {c.name}
              </Select.Option>))}
          </Select>
        </Item>
        <Item name="targetMoney" {...formItemLayout} label="目标金额">
          <InputNumber />
        </Item>
        <Item name="content" {...formItemLayout} label="内容">
          <ReactQuill />
        </Item>
        <Item name="timeRange" {...formItemLayout} label="生效时间">
          <DatePicker.RangePicker />
        </Item>
        <Item {...tailItemLayout}>
          <Button htmlType="submit" >提交</Button>
        </Item>
      </Form>
    );
  }
}