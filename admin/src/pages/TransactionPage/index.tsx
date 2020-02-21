import React, { useState, useRef, useEffect } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import ProTable, { ProColumns, ActionType } from '@ant-design/pro-table';
import { SorterResult } from 'antd/lib/table/interface';
import { TableListItem } from './data.d';
import { queryTransaction } from './service';

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
  const { data } = await queryTransaction({
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
      sorter: true,
      dataIndex: 'id',
    },
    {
      title: '哈希',
      dataIndex: 'hash',
    },
    {
      title: '类型',
      dataIndex: 'type',
      hideInSearch: true,
      valueEnum: {
        Donation: { text: '捐款' },
        Recharge: { text: '充值' },
      },
    },
    {
      title: '金额',
      dataIndex: 'money',
      hideInSearch: true,
      renderText: m => `${m} 元`,
    },
    {
      title: '时间',
      dataIndex: 'createdTime',
      hideInSearch: true,
    },
    {
      title: '付款者',
      dataIndex: 'payer',
      hideInSearch: true,
      renderText: (_, record) => record?.payer?.nickName,
    },
    {
      title: '收款者',
      dataIndex: 'payee',
      hideInSearch: true,
      renderText: (_, record) => record?.payee?.nickName,
    },
    {
      title: '项目',
      dataIndex: 'project',
      hideInSearch: true,
      renderText: (_, record) => record?.project?.name,
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
