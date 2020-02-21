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
      phoneNumber: '15180451191',
      nickName: '75500',
      avatar: 'https://n.sinaimg.cn/gongyi/160/w640h320/20200201/27ae-intiarp9983791.jpg',
      presentation: '个人介绍',
      profession: '学生',
      address: '地址',
      sex: 0,
      birthday: '1998-01-01 01:01:01',
      money: 1000,
      bumoAddress: '区块地址',
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
  'POST /api/admin/users/list': getProjects,
};
