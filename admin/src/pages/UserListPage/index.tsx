import React, { useState, useRef, useEffect } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import ProTable, { ProColumns, ActionType } from '@ant-design/pro-table';
import { SorterResult } from 'antd/lib/table/interface';
import { TableListItem } from './data.d';
import { queryUser } from './service';

/**
 * 添加节点
 * @param fields
 */

const handleListQuery = async (params: any) => {
  const { current, pageSize, sorter, ...restParams } = params!;
  const pageParam = {
    ...sorter,
    page: current,
    size: pageSize,
  };
  const { data } = await queryUser({
    ...restParams,
    pageParam,
  });
  return {
    total: data.total,
    data: data.content,
    success: true,
    pageSize: pageSize ?? 10,
    current: current ?? 1,
  };
};

const TableList: React.FC<{}> = () => {
  const [sorter, setSorter] = useState<{ field?: string; direction?: string }>({});
  const actionRef = useRef<ActionType>();
  const reload = () => {
    if (actionRef.current) {
      actionRef.current.reload();
    }
  };
  useEffect(reload, [sorter]);
  const columns: ProColumns<TableListItem>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
    },
    {
      title: '手机号',
      dataIndex: 'phoneNumber',
    },
    {
      title: '昵称',
      dataIndex: 'nickName',
    },
    {
      title: '头像',
      dataIndex: 'avatar',
      hideInSearch: true,
      render: (_, rocord) => (
        <img style={{ width: '50px', height: '50px' }} src={rocord.avatar} alt="封面" />
      ),
    },
    {
      title: '简介',
      dataIndex: 'presentation',
      hideInSearch: true,
    },
    {
      title: '职业',
      dataIndex: 'profession',
      hideInSearch: true,
    },
    {
      title: '地址',
      dataIndex: 'address',
      hideInSearch: true,
    },
    {
      title: '性别',
      dataIndex: 'sex',
      hideInSearch: true,
      valueEnum: {
        0: { text: '男' },
        1: { text: '女' },
      },
    },
    {
      title: '出生日期',
      sorter: true,
      hideInSearch: true,
      dataIndex: 'birthday',
    },
    {
      title: '金额',
      hideInSearch: true,
      dataIndex: 'money',
    },
    {
      title: '区块地址',
      hideInSearch: true,
      dataIndex: 'bumoAddress',
    },
  ];

  return (
    <PageHeaderWrapper>
      <ProTable<TableListItem>
        headerTitle="查询表格"
        actionRef={actionRef}
        rowKey="key"
        onChange={(_, _filter, _sorter) => {
          const st = _sorter as SorterResult<TableListItem>;
          if (st.order) {
            const direction = st.order === 'ascend' ? 'asc' : 'desc';
            setSorter({ field: `${st.field}`, direction });
          }
        }}
        params={{
          sorter,
        }}
        request={handleListQuery}
        columns={columns}
        rowSelection={{}}
      />
    </PageHeaderWrapper>
  );
};

export default TableList;
