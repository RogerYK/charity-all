import { TableListItem } from './data';

// mock tableListDataSource
let tableListDataSource: TableListItem[] = [];

for (let i = 0; i < 8; i += 1) {
  tableListDataSource.push({
    key: i,
    id: i,
    uniqueId: i,
    hash: 'hash',
    type: 'Donation',
    money: 100,
    createdTime: '2019-07-16 14:30:00',
    project: { id: 1, name: '玉洪救灾' },
    payer: { id: 1, nickname: '袁坤' },
    payee: { id: 1, nickname: '袁坤' },
  });
}

function getTransaction(
  req: { url: any },
  res: {
    json: (arg0: { errCode: number; data: any }) => void;
  },
  u: any,
) {
  const data = {
    content: tableListDataSource,
    total: tableListDataSource.length,
  };
  const result = {
    errCode: 0,
    data,
  };

  return res.json(result);
}

export default {
  'GET api/admin/transaction/list': getTransaction,
};
