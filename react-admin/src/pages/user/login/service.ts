import request from '../../../utils/request';
import { FromDataType } from './index';

export async function fakeAccountLogin(params: FromDataType) {
  return request('/auth/login', {
    method: 'POST',
    data: params,
  });
}

export async function getFakeCaptcha(mobile: string) {
  return request(`/auth/login/captcha?mobile=${mobile}`);
}
