import request from '../../../utils/request';
import { TableListParams } from './data';

export async function queryProject(params: TableListParams) {
  return request('/transaction/list', {
    params,
  });
}

export async function removeProject(id: number) {
  return request('/transaction/delete', {
    method: 'POST',
    data: {
      id,
    },
  });
}
