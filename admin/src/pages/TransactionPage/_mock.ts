import { Request, Response } from 'express';
import { TableListItem } from './data.d';

// mock tableListDataSource
const genList = (current: number, pageSize: number) => {
  const tableListDataSource: TableListItem[] = [];

  for (let i = 0; i < pageSize; i += 1) {
    const index = (current - 1) * 10 + i;
    tableListDataSource.push({
      key: index,
      id: index,
      hash: 'fafafaffa',
      type: 'Donation',
      money: 100,
      createdTime: '2020-01-01 01:01:01',
      payer: {
        id: index,
        nickName: '75500',
        avatar: '',
      },
      payee: {
        id: index,
        nickName: '75501',
        avatar: '',
      },
      project: {
        id: index,
        name: '武汉',
        img: '',
      },
    });
  }
  return tableListDataSource;
};

const tableListDataSource = genList(1, 100);

function getProjects(req: Request, res: Response, u: string) {
  let url = u;
  if (!url || Object.prototype.toString.call(url) !== '[object String]') {
    // eslint-disable-next-line prefer-destructuring
    url = req.url;
  }
  const { current = 1, pageSize = 10 } = req.query;
  const content = tableListDataSource.slice(current - 1, current - 1 + pageSize);
  const result = {
    errCode: 0,
    msg: '',
    data: {
      total: 103,
      content,
    },
  };

  return res.json(result);
}

export default {
  'POST /api/admin/transactions/list': getProjects,
};
