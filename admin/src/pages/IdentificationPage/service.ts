import request from '@/utils/request';
import { IdentificationListParams } from './data.d';

export async function queryIdentification(params?: IdentificationListParams) {
  return request('/identifications/list', {
    method: 'POST',
    data: params,
  });
}

export async function rejectIdentification(id: number) {
  return request('/identifications/reject', {
    method: 'POST',
    data: {
      id,
    },
  });
}

export async function passIdentification(id: number) {
  return request('/identifications/pass', {
    method: 'POST',
    data: {
      id,
    },
  });
}
