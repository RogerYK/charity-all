import { Button, Card, Col, Form, Input, Row, Select, Table } from 'antd';
import React, { Component, Fragment } from 'react';

import { Dispatch } from 'redux';
import { FormComponentProps } from 'antd/es/form';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { SorterResult } from 'antd/es/table';
import { connect } from 'dva';
import { ColumnProps } from 'antd/lib/table';
import { StateType } from './model';
import { TableListItem, TableListPagination, TableListParams } from './data.d';

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

/* eslint react/no-multi-comp:0 */
@connect(
  ({
    projectCommentList,
    loading,
  }: {
    projectCommentList: StateType;
    loading: {
      models: {
        [key: string]: boolean;
      };
    };
  }) => ({
    listTableList: projectCommentList,
    loading: loading.models.rule,
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
      title: '评论者',
      dataIndex: 'commenter',
      render: u => (
        <span>
          id:{u.id},昵称:{u.nickname}
        </span>
      ),
    },
    {
      title: '内容',
      dataIndex: 'content',
    },
    {
      title: '评论日期',
      dataIndex: 'createdTime',
    },
    {
      title: '操作',
      render: (text, record) => (
        <Fragment>
          <Button type="danger">删除</Button>
        </Fragment>
      ),
    },
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: 'projectCommentList/fetch',
    });
  }

  handleStandardTableChange = (
    pagination: Partial<TableListPagination>,
    filtersArg: Record<keyof TableListItem, string[]>,
    sorter: SorterResult<TableListItem>,
  ) => {
    const { dispatch } = this.props;
    const { formValues } = this.state;

    const filters = Object.keys(filtersArg).reduce((obj, key) => {
      const newObj = { ...obj };
      newObj[key] = getValue(filtersArg[key]);
      return newObj;
    }, {});

    const params: Partial<TableListParams> = {
      page: pagination.current,
      pageSize: pagination.pageSize,
      ...formValues,
      ...filters,
    };
    if (sorter.field) {
      params.sorter = `${sorter.field}_${sorter.order}`;
    }

    dispatch({
      type: 'listTableList/fetch',
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
      type: 'projectCommentList/fetch',
      payload: {},
    });
  };

  handleSearch = (e: React.FormEvent) => {
    e.preventDefault();

    const { dispatch, form } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;

      this.setState({
        formValues: fieldsValue,
      });

      dispatch({
        type: 'projectCommentList/fetch',
        payload: fieldsValue,
      });
    });
  };

  renderForm() {
    const { form } = this.props;
    const { getFieldDecorator } = form;
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="ID">
              {getFieldDecorator('id')(<Input placeholder="评论ID" />)}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="作者ID">
              {getFieldDecorator('commenterId')(<Input placeholder="作者ID" />)}
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
            />
          </div>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default Form.create<TableListProps>()(TableList);
