import request from '@/utils/request';
import { TransactionListParams } from './data.d';

export async function queryTransaction(params?: TransactionListParams) {
  return request('/transactions/list', {
    method: 'POST',
    data: params,
  });
}
