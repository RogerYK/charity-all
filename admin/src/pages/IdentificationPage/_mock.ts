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
      userId: index,
      createdTime: '2019-12-01 01:01:01',
      updatedTime: '2019-12-01 01:01:01',
      identificationType: 'Personal',
      identificationState: 'Apply',
      identityCardName: '张三',
      identityCardNumber: '41132919990101xxxx',
      identityCardPicture:
        'https://n.sinaimg.cn/gongyi/160/w640h320/20200201/27ae-intiarp9983791.jpg',
      email: 'name@outlook.com',
      phoneNumber: '15180888488',
      userPicture: 'https://n.sinaimg.cn/gongyi/160/w640h320/20200201/27ae-intiarp9983791.jpg',
      sex: 'Man',
      nation: 'China',
      province: 'JiangXi',
      city: 'Nanchang',
      region: '青山湖',
      detailAddress: '南京东路135号',
      profession: '学生',
      company: 'Google',
      introduction: '热爱公益',
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
  'POST /api/admin/identifications/list': getProjects,
  'POST /api/admin/identifications/pass': okResponse,
  'POST /api/admin/identifications/reject': okResponse,
};
