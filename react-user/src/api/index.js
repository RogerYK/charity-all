import r_1mn  from './assets/r-1.jpg'
import r_2 from './assets/r-2.jpg'
import s_1 from './assets/s-1.jpg'
import s_2 from './assets/s-2.jpg'
import s_3 from './assets/s-3.jpg'
import d_1 from './assets/d-1.jpg'
import d_2 from './assets/d-2.jpg'
import d_3 from './assets/d-3.jpg'
import i_1 from './assets/i-1.jpg'
import i_2 from './assets/i-2.jpg'
import i_3 from './assets/i-3.jpg'
import charityIco from './assets/charity.png'

import activitIco from './assets/144829627.jpg'

import slider1 from './assets/slider-1.webp'
import slider2 from './assets/slider-2.jpg'
import slider3 from './assets/slider-3.webp'

import request from '@/utils/request'
import { getToken } from '../utils/auth';

export function getRecommendProjects() {
  return request({
    url: '/project/recommend',
    method: 'get',
  })
}

export function getRecommendBanners() {
  return Promise.resolve({
    errCode: 0,
    data: [{
      img: slider1,
      link: '/',
    }, {
      img: slider2,
      link: '/',
    }, {
      img: slider3,
      link: '/',
    }]
  })
}

export function getProjectDetail(id) {
  return request({
    url: '/project/',
    method: 'get',
    params: {id}
  })
}

export function getCategories() {
return Promise.resolve({
  errCode: 0,
  data: [{
    name: '扶贫救灾'
  },{
    name: '疾病救助'
  },{
    name: '教育助学'
  },{
    name: '自然保护'
  },{
    name: '其他'
  }]
})
}

export function getCategoryProjectsInfo(category) {
  return Promise.resolve({
    errCode: 0,
    data: {
      total: 109,
      pageCount: 10,
    }
  })
}

export function getProjects(category, page) {
  return Promise.resolve({
    errCode: 0,
    data: new Array(12).fill({
      name: '玉洪灾救援',
      img: d_1,
      author: {
        nickname: '红十字',
        avatarLink: charityIco,
      },
      raisedMoney: Number((Math.random()*50000).toFixed(2)),
      targetMoney: Number((Math.random()*5000+10000).toFixed(2)),
      donorCount: Number((Math.random()*500+10).toFixed(0)),
    })
  })
}

export function getHotProjects() {
  // return Promise.resolve({
  //   errCode: 0,
  //   data: new Array(5).fill({
  //     name: '玉洪灾区救援',
  //     img: d_1,
  //     author: {
  //       nickname: '红十字',
  //       avatarLink: charityIco,
  //     },
  //     raisedMoney: 2000,
  //     targetMoney: 4000,
  //     donorCount: 200,
  //     summary: `玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。`
  //   })
  // })
  return request({
    url: '/project/hot',
    method: 'get'
  })
}

export function getHotActivities() {
  // return Promise.resolve({
  //   errCode: 0,
  //   data: new Array(5).fill({
  //     name: '99公益日',
  //     img: activitIco,
  //   })
  // })
  return request({
    url: '/activity/hot',
    method: 'get'
  })
}


export function login(phoneNumber, password) {
  const data = {phoneNumber, password}
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function sign(phoneNumber, password) {
  const data = {phoneNumber, password}
  return request({
    url: '/user/sign',
    method: 'post',
    data
  })
}


export function getFavorProjects(page=0, size=9, direction='ASC', field='createTime') {
  // return Promise.resolve({
  //   errCode: 0,
  //   data: new Array(4).fill({
  //     name: '玉洪灾区救援',
  //     img: d_1,
  //     author: {
  //       nickname: '红十字',
  //       avatarLink: charityIco,
  //     },
  //     raisedMoney: 2000,
  //     targetMoney: 4000,
  //     donorCount: 200,
  //     summary: `玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。`
  //   }),
  // })
  const data = {page, size, direction, field}
  return request({
    url: '/user/project/favor',
    method: 'get',
    data
  })
}

export function getReleaseProjects(page=0, size=9, direction='ASC', field='createTime') {
  // return Promise.resolve({
  //   errCode: 0,
  //   data: [],
  // })
  const data = {page, size, direction, field}
  return request({
    url: '/user/project/release',
    method: 'get',
    data
  })
}


export function getRecords(page=0, size=5, direction='ASC', field='createTime') {
  const data = {page, size, direction, field}
  return request({
    url: '/user/record/donation',
    method: 'get',
    data
  })
}

export function getCurUserInfo() {
  const token = getToken()
  if (token) {
    return request({
      url: '/user/',
      method: 'get',
    })
  } else {
    return Promise.reject({errCode: 203, msg: '用户未登录'})
  }
}

export function updateUser(data) {
  return request({
    url: '/user/update',
    method: 'put',
    data
  })
}

export function upload(formData) {
  return request({
    url: '/upload/',
    method: 'post',
    headers: {'Content-Type': 'multipart/form-data'},
    data: formData
  })
}

export function donate(projectId, amount) {
  return request({
    url: '/transaction/donate',
    method: 'post',
    data: {projectId, amount}
  })
}

export function saveProject(form) {
  return request({
    url: '/project/save',
    method: 'post',
    data: form,
  })
}

export function getAllCategories() {
  return request({
    url: '/category/all',
    method: 'get',
  })
}

export function getProjectsByCategory(categoryId, page, size, field, direction) {
  return request({
    url: '/project/byCategory',
    method: 'get',
    params: {categoryId, page, size, field, direction},
  })
}

export function searchProjectByName(name, page, size, field, direction) {
  return request({
    url: '/project/byName',
    method: 'get',
    params: {name, page, size, field, direction}
  })
}