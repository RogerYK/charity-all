import request from '../../../utils/request';
import { TableListParams } from './data';

export async function queryProject(params: TableListParams) {
  return request('/news/list', {
    params,
  });
}

export async function removeProject(id: number) {
  return request('/news/delete', {
    method: 'POST',
    data: {
      id,
    },
  });
}
