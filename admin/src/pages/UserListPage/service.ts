import request from '@/utils/request';
import { UserListParams } from './data.d';

export async function queryUser(params?: UserListParams) {
  return request('/users/list', {
    method: 'POST',
    data: params,
  });
}
