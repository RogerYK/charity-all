import request from '@/utils/request';

export async function queryCurrent(): Promise<any> {
  return request('/admins/current');
}

export async function queryNotices(): Promise<any> {
  return request('/api/notices');
}
