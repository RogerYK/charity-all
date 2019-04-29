import React, { Component } from 'react'
import ReactQuill from 'react-quill'
import moment from 'moment'
import { Form, Input, Upload, Icon, Button, DatePicker, InputNumber, message, Select } from 'antd';
import config from '../../../../utils/config';
import { inject, observer } from 'mobx-react';
import api from '../../../../api';

@inject('releaseStore')
@observer
export default class AddProjectForm extends Component {

  constructor(props) {
    super(props)
    this.state = {
      form: {
        name: '',
        img: '',
        gallery: '',
        categoryId: '',
        content: '',
        targetMoney: '',
        startTime: moment(),
        endTime: moment(),
      },
      fileList: [],
      imgList: [],
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

  handleFileListChange = (info) => {
    this.setState({fileList: info.fileList});
  }

  handleImgListChange = (info) => {
    const fileList = info.fileList.filter(f => f.status !== 'error')
    this.setState({imgList: fileList.slice(-1)})
  }

  handleChange(e, key) {
    let form = {...this.state.form}
    form[key] = e.target.value
    this.setState({form})
  }

  handleValueChange(value, key) {
    let form = {...this.state.form}
    console.log(value)
    form[key] = value
    this.setState({form})
  }

  handleTimeChange(value, key) {
    let form = {...this.state.form}
    form[key] = value
    this.state({form})
  }

  handleSubmit = () => {
    const data = {
      id: this.props.id,
      ...this.state.form,
      img: this.state.imgList[0].response.data,
      gallery: this.state.fileList.map(f => f.response.data)
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
    const form = this.state.form
    const formItemLayout = {labelCol: {span: 4}, wrapperCol: {span: 20}}
    const tailItemLayout = {wrapperCol: {span: 20, offset: 4}}
    return (
      <Form>
        <Item {...formItemLayout} label="项目名称">
          <Input value={form.name}
            onChange={e => this.handleChange(e, 'name')}
          ></Input>
        </Item>
        <Item {...formItemLayout} label="项目封面" extra="用于在列表卡片中显示">
          <Upload
            action={`${config.baseURL}/upload/`}
            multiple={false}
            disabled={this.state.imgList.length > 0}
            fileList={this.state.imgList}
            onChange={e => this.handleImgListChange(e)}
          >
            <Button><Icon type="upload" /> 点击上传图片</Button>
          </Upload>
        </Item>
        <Item {...formItemLayout} label="项目图片">
          <Upload
            action={`${config.baseURL}/upload/`}
            fileList={this.state.fileList}
            onChange={this.handleFileListChange}
          >
            <Button>
              <Icon type="upload" /> 点击上传图片
            </Button>
          </Upload>
        </Item>
        <Item {...formItemLayout} label="类别">
          <Select 
            value={form.categoryId}
            onChange={v => this.handleValueChange(v, 'categoryId')}
          >
          {this.state.categories.map(c => (
          <Select.Option value={c.id} key={c.id}>
          {c.name}
          </Select.Option>))}
          </Select>
        </Item>
        <Item {...formItemLayout} label="目标金额">
          <InputNumber
            value={form.targetMoney}
            onChange={v => this.handleValueChange(v, 'targetMoney')}
          ></InputNumber>
        </Item>
        <Item {...formItemLayout} label="内容">
          <ReactQuill
            value={form.content}
            onChange={v => this.handleValueChange(v, 'content')}
          ></ReactQuill>
        </Item>
        <Item {...formItemLayout} label="生效时间">
          <DatePicker.RangePicker value={[moment(form.startTime), moment(form.endTime)]}
            onChange={v => {
              this.handleValueChange(v[0].format('YYYY-MM-DD HH:mm:ss'), 'startTime')
              this.handleValueChange(v[1].format('YYYY-MM-DD HH:mm:ss'), 'endTime')
            }}
          >
          </DatePicker.RangePicker>
        </Item>
        <Item {...tailItemLayout}>
          <Button onClick={this.handleSubmit}>提交</Button>
        </Item>
      </Form>
    )
  }
}