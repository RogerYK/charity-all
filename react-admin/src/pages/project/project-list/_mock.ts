import { TableListItem } from './data.d';

// mock tableListDataSource
let tableListDataSource: TableListItem[] = [];

for (let i = 0; i < 8; i += 1) {
  tableListDataSource.push({
    key: i,
    id: i,
    name: '玉洪救灾',
    img:
      'http://p.qpic.cn/gongyi/e106161bfc12e95686ea68a523affc171b3109db8866d01abf8b417675285a481f172a859c1ac4da5c50eb3fafd49c16aa915793e6be6156/500',
    gallery: [],
    content: '',
    summary: '',
    raisedMoney: 1000,
    targetMoney: 2000,
    createdTime: '2019-07-16 14:30:00',
    startTime: '2019-07-16 14:30:00',
    endTime: '2019-07-16 14:30:00',
    bumoAddress: '',
    updateTime: '',
    donorCount: 7,
    author: { id: 1, nickname: '袁坤' },
    category: { id: 1, name: '袁坤' },
  });
}

function getProject(
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
  'GET /project/list': getProject,
};
