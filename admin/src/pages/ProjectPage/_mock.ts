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
      name: `project name ${index}`,
      img: 'https://n.sinaimg.cn/gongyi/160/w640h320/20200201/27ae-intiarp9983791.jpg',
      gallery: ['https://n.sinaimg.cn/gongyi/160/w640h320/20200201/27ae-intiarp9983791.jpg'],
      content: 'project content',
      summary: 'summary',
      raisedMoney: Math.floor(Math.random() * 100),
      targetMoney: Math.floor(Math.random() * 1000),
      createdTime: '2019-12-01 01:01:01',
      startTime: '2019-12-01 01:01:01',
      endTime: '2019-12-01 01:01:01',
      bumoAddress: '',
      donorCount: Math.floor(Math.random() * 10),
      status: Math.floor(Math.random() * 10) % 4,
      author: {
        id: 1,
        nickname: 'yuankun',
      },
      category: {
        id: 1,
        name: 'jibing',
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

function okResponse(_, res: Response) {
  const result = {
    errCode: 0,
    msg: '',
  };
  return res.json(result);
}

export default {
  'POST /api/admin/projects/list': getProjects,
  'POST /api/admin/projects/allow': okResponse,
  'POST /api/admin/projects/delete': okResponse,
};
