import request from '@/utils/request';

export async function querySumUserCount() {
  return request('/analysis/user/count/sum');
}

export async function queryUserCountByRange(params: { startTime: string; endTime: string }) {
  return request('/analysis/user/count', { params });
}

export async function querySumProjectCount() {
  return request('/analysis/project/count/sum');
}

export async function queryProjectCount(params: { startTime: string; endTime: string }) {
  return request('/analysis/project/count', { params });
}

export async function querySumTransaction() {
  return request('/analysis/transaction/count/sum');
}

export async function queryTransactionCount(params: { startTime: string; endTime: string }) {
  return request('/analysis/transaction/count', { params });
}
