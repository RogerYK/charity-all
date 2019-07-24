import request from '../../../utils/request';
import { TableListParams } from './data.d';

export async function queryProject(params: TableListParams) {
  return request('/project/list', {
    params,
  });
}

export async function removeProject(id: number) {
  return request('/project/delete', {
    method: 'POST',
    data: {
      id,
    },
  });
}
