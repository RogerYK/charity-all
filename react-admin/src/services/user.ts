import request from '@/utils/request';

export async function queryCurrent(): Promise<any> {
  return request('/user/current');
}

export async function queryNotices(): Promise<any> {
  return request('/notices');
}
