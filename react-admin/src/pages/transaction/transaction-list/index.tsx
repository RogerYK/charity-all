import { Button, Card, Col, Form, Input, Row } from 'antd';
import React, { Component, Fragment } from 'react';

import { Dispatch } from 'redux';
import { FormComponentProps } from 'antd/es/form';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import Table, { TableProps } from 'antd/es/table';
import { connect } from 'dva';
import { StateType } from './model';
import { TableListItem, TableListParams } from './data';

import styles from './style.less';
import { ColumnProps } from 'antd/lib/table';

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
    transactionList,
    loading,
  }: {
    transactionList: StateType;
    loading: {
      models: {
        [key: string]: boolean;
      };
    };
  }) => ({
    listTableList: transactionList,
    loading: loading.models.transsactionList,
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
      title: '唯一ID',
      dataIndex: 'uniqueId',
    },
    {
      title: '交易哈希',
      dataIndex: 'hash',
    },
    {
      title: '付款人',
      dataIndex: 'payer',
      render: payer => (
        <>
          {payer ? (
            <span>
              id:{payer.id}, 昵称:{payer.nickname}
            </span>
          ) : (
            <></>
          )}
        </>
      ),
    },
    {
      title: '收款人',
      dataIndex: 'payee',
      render: payee => (
        <>
          {payee ? (
            <span>
              id:{payee.id}, 昵称:{payee.nickname}
            </span>
          ) : (
            <></>
          )}
        </>
      ),
    },
    {
      title: '交易金额',
      dataIndex: 'money',
      align: 'right',
      render: (val: number) => `${val} 元`,
    },
    {
      title: '创建时间',
      dataIndex: 'createdTime',
      align: 'right',
    },
    {
      title: '操作',
      render: (text, record) => (
        <Fragment>
          <a>删除</a>
        </Fragment>
      ),
    },
  ];

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: 'transactionList/fetch',
    });
  }

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
      type: 'transactionList/fetch',
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
      type: 'transactionList/fetch',
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
        type: 'transactionList/fetch',
        payload: fieldsValue,
      });
    });
  };

  renderSimpleForm() {
    const { form } = this.props;
    const { getFieldDecorator } = form;
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="ID">
              {getFieldDecorator('transactionId')(<Input placeholder="项目ID" />)}
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
            />
          </div>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default Form.create<TableListProps>()(TableList);
