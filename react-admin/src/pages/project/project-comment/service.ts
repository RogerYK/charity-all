import request from '../../../utils/request';
import { TableListParams } from './data.d';

export async function queryRule(params: TableListParams) {
  return request('/project/comment/list', {
    params,
  });
}

export async function removeRule(params: TableListParams) {
  return request('/project/comment/delete', {
    method: 'POST',
    data: {
      ...params,
    },
  });
}
