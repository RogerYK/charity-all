import { Button, message } from 'antd';
import React, { useState, useRef, useEffect } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import ProTable, { ProColumns, ActionType } from '@ant-design/pro-table';
import { SorterResult } from 'antd/lib/table/interface';
import { TableListItem } from './data.d';
import { queryIdentification, passIdentification, rejectIdentification } from './service';

/**
 * 添加节点
 * @param fields
 */

const handlePass = async (id: number) => {
  const hide = message.loading('正在通过');
  try {
    await passIdentification(id);
    hide();
    message.success('通过');
    return true;
  } catch (error) {
    hide();
    message.error('失败');
    return false;
  }
};

/**
 *  删除节点
 * @param selectedRows
 */
const handleReject = async (id: number) => {
  const hide = message.loading('正在删除');
  try {
    await rejectIdentification(id);
    hide();
    message.success('删除成功，即将刷新');
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试');
    return false;
  }
};

const handleQuery = async (params: any) => {
  const { current, pageSize, sorter, ...restParams } = params!;
  const pageParam = {
    ...sorter,
    page: current,
    size: pageSize,
  };
  const { data } = await queryIdentification({
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

const TableImg: React.FC<{ src: string }> = ({ src }) => (
  <img style={{ width: '50px', height: '50px' }} src={src} alt="图片" />
);

const TableList: React.FC<{}> = () => {
  const [sorter, setSorter] = useState({});
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
      title: '用户',
      dataIndex: 'userId',
    },
    {
      title: '创建时间',
      dataIndex: 'createdTime',
      hideInSearch: true,
    },
    {
      title: '更新时间',
      dataIndex: 'updatedTime',
      hideInSearch: true,
    },
    {
      title: '认证类型',
      hideInSearch: true,
      dataIndex: 'identificationType',
      valueEnum: {
        Personal: { text: '个人' },
        Organization: { text: '组织' },
      },
    },
    {
      title: '状态',
      hideInSearch: true,
      dataIndex: 'identificationState',
      valueEnum: {
        Apply: { text: '申请中' },
        Pass: { text: '通过' },
        Reject: { text: '失败' },
      },
    },
    {
      title: '身份证姓名',
      hideInSearch: true,
      dataIndex: 'identityCardName',
    },
    {
      title: '身份证号码',
      hideInSearch: true,
      dataIndex: 'identityCardNumber',
    },
    {
      title: '身份证半身照',
      hideInSearch: true,
      dataIndex: 'identityCardPicture',
      render: (_, record) => <TableImg src={record.identityCardPicture} />,
    },
    {
      title: '邮箱',
      dataIndex: 'email',
      hideInSearch: true,
    },
    {
      title: '手机号码',
      dataIndex: 'phoneNumber',
      hideInSearch: true,
    },
    {
      title: '证件照片',
      dataIndex: 'userPicture',
      hideInSearch: true,
      render: (_, record) => <TableImg src={record.userPicture} />,
    },
    {
      title: '性别',
      hideInSearch: true,
      dataIndex: 'sex',
      valueEnum: {
        Woman: { text: '女' },
        Man: { text: '男' },
      },
    },
    {
      title: '位置',
      hideInSearch: true,
      renderText: (_, { nation, province, city, region, detailAddress }) =>
        `${nation}${province}${city}${region}${detailAddress}`,
    },
    {
      title: '职业',
      hideInSearch: true,
      dataIndex: 'profession',
    },
    {
      title: '公司',
      hideInSearch: true,
      dataIndex: 'company',
    },
    {
      title: '操作',
      render: (_, record) => (
        <>
          {record.identificationState === 'Apply' ? (
            <>
              <Button
                onClick={async () => {
                  await handlePass(record.id);
                  reload();
                }}
                style={{ marginRight: '20px' }}
                type="primary"
              >
                通过
              </Button>
              <Button
                onClick={async () => {
                  await handleReject(record.id);
                  reload();
                }}
                type="danger"
              >
                拒绝
              </Button>
            </>
          ) : (
            <></>
          )}
        </>
      ),
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
        tableAlertRender={selectedRowKeys => (
          <div>
            已选择 <a style={{ fontWeight: 600 }}>{selectedRowKeys.length}</a> 项&nbsp;&nbsp;
          </div>
        )}
        request={handleQuery}
        columns={columns}
        rowSelection={{}}
      />
    </PageHeaderWrapper>
  );
};

export default TableList;
