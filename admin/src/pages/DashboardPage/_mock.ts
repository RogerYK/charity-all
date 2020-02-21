import moment from 'moment';
import { CountData } from './data.d';

const timeFormat = 'YYYY-MM-DD';

const getData = (start: moment.Moment, end: moment.Moment) => {
  const ds: CountData[] = [];
  for (; start.isBefore(end); start.add(1, 'days')) {
    const date = start.format(timeFormat);
    ds.push({
      date,
      count: Math.floor(Math.random() * 50),
    });
  }
  return ds;
};

const data = getData(moment().subtract(14, 'days'), moment());
const total = data.reduce((a, b) => a + b.count, 0);

const floatFun = (v: CountData) => ({ ...v, count: v.count + Math.floor(Math.random() * 50) });

const okResponse = (v: any) => ({ errCode: 0, data: v });

export default {
  'GET /api/admin/analysis/user/count/sum': okResponse({ total }),
  'GET /api/admin/analysis/user/count': okResponse({ items: data.map(floatFun) }),
  'GET /api/admin/analysis/project/count/sum': okResponse({ total }),
  'GET /api/admin/analysis/project/count': okResponse({ items: data.map(floatFun) }),
  'GET /api/admin/analysis/transaction/count/sum': okResponse({ total }),
  'GET /api/admin/analysis/transaction/count': okResponse({ items: data.map(floatFun) }),
};
