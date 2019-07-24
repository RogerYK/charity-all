import { TableListItem } from './data.d';

// mock tableListDataSource
let tableListDataSource: TableListItem[] = [];

for (let i = 0; i < 8; i += 1) {
  tableListDataSource.push({
    key: i,
    id: i,
    content: '评论内容',
    commenter: { id: 1, nickname: '袁坤' },
    createdTime: '2018-07-17 14:14:00',
    replyComment: { id: 0 },
    // children: [
    //   {
    //     key: i * 10 + 1,
    //     id: 0,
    //     content: 'children评论',
    //     commenter: { id: 1, nickname: '袁坤' },
    //     createdTime: '2019-07-17 14:16:00',
    //   },
    // ],
  });
}

function getComment(
  req: { url: any },
  res: {
    json: (arg0: { errCode: number; data: any }) => void;
  },
  u: any,
) {
  let dataSource = tableListDataSource;
  const data = {
    content: dataSource,
    total: dataSource.length,
  };
  const result = {
    errCode: 0,
    data,
  };

  return res.json(result);
}

export default {
  'GET /project/comment/list': getComment,
};
