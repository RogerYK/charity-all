import { Button, Card, Col, Form, Input, Row } from 'antd';
import React, { Component, Fragment } from 'react';

import { Dispatch } from 'redux';
import { FormComponentProps } from 'antd/es/form';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import Table, { TableProps } from 'antd/es/table';
import { connect } from 'dva';
import { ColumnProps } from 'antd/lib/table';
import { StateType } from './model';
import { TableListItem, TableListParams } from './data.d';

import styles from './style.less';

const FormItem = Form.Item;
const getValue = (obj: { [x: string]: string[] }) =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(',');

interface TableListProps extends FormComponentProps {
  dispatch: Dispatch<any>;
  loading: boolean;
  listTableList: StateType;
}

interface TableListState {
  formValues: { [key: string]: string };
}

const statusLabels = {
  0: '申请中',
  1: '已上线',
  2: '众筹成功',
  3: '众筹失败',
  4: '已删除',
};

function getStatusLabel(status: number) {
  return statusLabels[status];
}

/* eslint react/no-multi-comp:0 */
@connect(
  ({
    projectList,
    loading,
  }: {
    projectList: StateType;
    loading: {
      models: {
        [key: string]: boolean;
      };
    };
  }) => ({
    listTableList: projectList,
    loading: loading.models.projectList,
  }),
)
class TableList extends Component<TableListProps, TableListState> {
  state: TableListState = {
    formValues: {},
  };

  columns: ColumnProps<TableListItem>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
    },
    {
      title: '项目名',
      dataIndex: 'name',
    },
    {
      title: '封面',
      dataIndex: 'img',
      render: val => <img style={{ width: 100 }} src={val} alt="封面" />,
    },
    {
      title: '状态',
      dataIndex: 'status',
      render: status => getStatusLabel(status),
    },
    {
      title: '详情',
      dataIndex: 'content',
      render: content => <Button>查看</Button>,
    },
    {
      title: '已筹款',
      dataIndex: 'raisedMoney',
      align: 'right',
      render: (val: string) => `${val} 元`,
    },
    {
      title: '目标金额',
      dataIndex: 'targetMoney',
      align: 'right',
      render: (val: string) => `${val} 元`,
    },
    {
      title: '创建时间',
      dataIndex: 'createdTime',
      align: 'right',
    },
    {
      title: '捐款人数',
      dataIndex: 'donorCount',
    },
    {
      title: '操作',
      dataIndex: 'id',
      render: (id, record) => (
        <Fragment>
          {record.status === 0 ? (
            <Button
              onClick={() => this.handleAllow(id)}
              style={{ marginRight: '20px' }}
              type="primary"
            >
              通过
            </Button>
          ) : (
            <></>
          )}
          <Button onClick={() => this.handleDelete(id)} type="danger">
            删除
          </Button>
        </Fragment>
      ),
    },
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: 'projectList/fetch',
    });
  }

  handleAllow = (id: number) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'projectList/allow',
      payload: id,
    });
  };

  handleDelete = (id: number) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'projectList/remove',
      payload: id,
    });
  };

  handleStandardTableChange: TableProps<TableListItem>['onChange'] = (
    pagination,
    filtersArg,
    sorter,
    ...rest
  ) => {
    const { dispatch } = this.props;
    const { formValues } = this.state;

    const filters = Object.keys(filtersArg).reduce((obj, key) => {
      const newObj = { ...obj };
      newObj[key] = getValue(filtersArg[key]);
      return newObj;
    }, {});

    const params: Partial<TableListParams> = {
      size: pagination.pageSize,
      ...formValues,
      ...filters,
    };
    if (sorter.field) {
      params.field = sorter.field;
      params.direction = sorter.order;
    }

    dispatch({
      type: 'projectList/fetch',
      payload: params,
    });
  };

  handleFormReset = () => {
    const { form, dispatch } = this.props;
    form.resetFields();
    this.setState({
      formValues: {},
    });
    dispatch({
      type: 'projectList/fetch',
      payload: {},
    });
  };

  handleSearch = (e: React.FormEvent) => {
    e.preventDefault();

    console.log('handle search');

    const { dispatch, form } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;
      this.setState({
        formValues: fieldsValue,
      });
      dispatch({
        type: 'projectList/fetch',
        payload: fieldsValue,
      });
    });
  };

  expandedRowRender = (project: TableListItem) => (
    <div>
      <Row>
        <Col span={4}>画廊</Col>
        <div>
          {project.gallery.map((img, i) => (
            <img key={i} src={img} alt="gallery" />
          ))}
        </div>
      </Row>
      <Row>
        <Col span={4}>介绍</Col>
        <div>{project.content}</div>
      </Row>
      <Row>
        <Col span={4}>开始日期</Col>
        <div>{project.startTime}</div>
      </Row>
      <Row>
        <Col span={4}>结束日期</Col>
        <div>{project.endTime}</div>
      </Row>
      <Row>
        <Col span={4}>区块地址</Col>
        <div>{project.bumoAddress}</div>
      </Row>
      <Row>
        <Col span={4}>发起者</Col>
        <div>
          用户名{project.author.nickname}, 用户ID:{project.author.id}
        </div>
      </Row>
      <Row>
        <Col span={4}>分类</Col>
        <div>
          类目{project.category.id}, 类目ID:{project.author.nickname}
        </div>
      </Row>
    </div>
  );

  renderSimpleForm() {
    const { form } = this.props;
    const { getFieldDecorator } = form;
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="ID">
              {getFieldDecorator('projectId')(<Input placeholder="项目ID" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="作者">
              {getFieldDecorator('authorId')(<Input placeholder="作者ID" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="类别">
              {getFieldDecorator('categoryId')(<Input placeholder="类目ID" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <span className={styles.submitButtons}>
              <Button type="primary" htmlType="submit">
                查询
              </Button>
              <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>
                重置
              </Button>
            </span>
          </Col>
        </Row>
      </Form>
    );
  }

  renderForm() {
    return this.renderSimpleForm();
  }

  render() {
    const {
      listTableList: { data },
      loading,
    } = this.props;
    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>{this.renderForm()}</div>
            <div className={styles.tableListOperator}></div>
            <Table
              loading={loading}
              dataSource={data.list}
              pagination={data.pagination}
              columns={this.columns}
              onChange={this.handleStandardTableChange}
              expandedRowRender={this.expandedRowRender}
            />
          </div>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default Form.create<TableListProps>()(TableList);
