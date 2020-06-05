import { DownOutlined } from '@ant-design/icons';
import { Button, Dropdown, Menu, message } from 'antd';
import React, { useState, useRef, useEffect } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import ProTable, { ProColumns, ActionType } from '@ant-design/pro-table';
import { SorterResult } from 'antd/lib/table/interface';
import { TableListItem } from './data.d';
import { queryProject, allowProject, removeProject } from './service';

/**
 * 添加节点
 * @param fields
 */

const handleAllow = async (id: number) => {
  const hide = message.loading('正在通过');
  try {
    await allowProject(id);
    hide();
    message.success('通过');
    return true;
  } catch (error) {
    hide();
    message.error('删除失败');
    return false;
  }
};

/**
 *  删除节点
 * @param selectedRows
 */
const handleRemove = async (selectedRows: TableListItem[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    await removeProject(selectedRows.map(r => r.id));
    hide();
    message.success('删除成功，即将刷新');
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试');
    return false;
  }
};

const handleListQuery = async (params: any) => {
  const { current, pageSize, sorter, status, ...restParams } = params!;
  const pageParam = {
    ...sorter,
    page: current,
    size: pageSize,
  };
  const { data } = await queryProject({
    ...restParams,
    statusList: status ? [status] : undefined,
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
      title: '项目名',
      dataIndex: 'name',
    },
    {
      title: '封面',
      dataIndex: 'img',
      hideInSearch: true,
      render: (_, rocord) => (
        <img style={{ width: '50px', height: '50px' }} src={rocord.img} alt="封面" />
      ),
    },
    {
      title: '状态',
      dataIndex: 'status',
      filterMultiple: true,
      hideInSearch: true,
      valueEnum: {
        0: { text: '创建中', status: 'Default' },
        1: { text: '申请中', status: 'Processing' },
        2: { text: '已上线', status: 'Default' },
        3: { text: '成功', status: 'Success' },
        4: { text: '失败', status: 'Warning' },
      },
    },
    {
      title: '已筹款',
      dataIndex: 'raisedMoney',
      hideInSearch: true,
      renderText: val => `${val} 元`,
    },
    {
      title: '目标金额',
      dataIndex: 'targetMoney',
      hideInSearch: true,
      renderText: val => `${val} 元`,
    },
    {
      title: '开始时间',
      sorter: true,
      hideInSearch: true,
      dataIndex: 'startTime',
    },
    {
      title: '截止时间',
      hideInSearch: true,
      dataIndex: 'endTime',
    },
    {
      title: '捐款人数',
      hideInSearch: true,
      dataIndex: 'donorCount',
    },
    {
      title: '操作',
      render: (_, record) => (
        <>
          {record.status === 1 ? (
            <Button
              onClick={async () => {
                await handleAllow(record.id);
                reload();
              }}
              style={{ marginRight: '20px' }}
              type="primary"
            >
              通过
            </Button>
          ) : (
            <></>
          )}
          <Button
            onClick={async () => {
              await handleRemove([record]);
              reload();
            }}
            type="danger"
          >
            删除
          </Button>
        </>
      ),
    },
  ];

  return (
    <PageHeaderWrapper>
      <ProTable<TableListItem>
        headerTitle="项目信息"
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
        toolBarRender={(action, { selectedRows }) => [
          selectedRows && selectedRows.length > 0 && (
            <Dropdown
              overlay={
                <Menu
                  onClick={async e => {
                    if (e.key === 'remove') {
                      await handleRemove(selectedRows);
                      action.reload();
                    }
                  }}
                  selectedKeys={[]}
                >
                  <Menu.Item key="remove">批量删除</Menu.Item>
                </Menu>
              }
            >
              <Button>
                批量操作 <DownOutlined />
              </Button>
            </Dropdown>
          ),
        ]}
        tableAlertRender={selectedRowKeys => (
          <div>
            已选择 <a style={{ fontWeight: 600 }}>{selectedRowKeys.length}</a> 项&nbsp;&nbsp;
          </div>
        )}
        request={handleListQuery}
        columns={columns}
        rowSelection={{}}
      />
    </PageHeaderWrapper>
  );
};

export default TableList;
