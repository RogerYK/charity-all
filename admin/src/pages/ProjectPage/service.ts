import request from '@/utils/request';
import { ProjectListParams } from './data.d';

export async function queryProject(params?: ProjectListParams) {
  return request('/projects/list', {
    method: 'POST',
    data: params,
  });
}

export async function removeProject(ids: number[]) {
  return request('/projects/delete', {
    method: 'POST',
    data: {
      ids,
    },
  });
}

export async function allowProject(id: number) {
  return request('/projects/allow', {
    method: 'POST',
    data: {
      id,
    },
  });
}
