import { TableListItem } from './data';

// mock tableListDataSource
let tableListDataSource: TableListItem[] = [];

for (let i = 0; i < 8; i += 1) {
  tableListDataSource.push({
    key: i,
    id: i,
    title: '新闻标题',
    img: 'http://p1.pstatp.com/large/tos-cn-i-0022/74f3ffb76fa14ec0bfc3b0c968a458aa',
    content: '新闻内容',
    introduce: '新闻介绍',
    createdTime: '2019-07-16 14:30:00',
    watchCount: 1,
    favorCount: 1,
    comentCount: 1,
    author: { id: 1, nickname: 'YK' },
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
  'GET api/admin/news/list': getTransaction,
};
