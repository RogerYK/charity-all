import request from '../utils/request'

const Project = {
  recommend: (n) => 
    request.get('/project/recommend', {params:{n}}),
  hot: () => 
    request.get('/project/hot'),
  byCategory: (categoryId, page = 0, size = 12, direction='asc', field='createTime') =>
    request.get('/project/byCategory',{params:{categoryId, page, size, direction, field}}),
  detail: (id) => 
    request.get('/project/', {params: {id}}),
  byName: (name, page = 0, size = 12, direction='asc', field='createTime') => 
    request.get('/project/byName', {params:{name, page, size, direction, field}}),
  favoritedBy: (userId) =>
    request.get('/project/favoritedBy', {params:{userId}}),
  releasedBy: (userId) =>
    request.get('/project/releasedBy', {params:{userId}}),
  save: (project) =>
    request.post('/project/', project),
  addSchedule: (data) =>
    request.post('/project/schedule', data)
}

const Comment = {
  byProjectId: (projectId, page=0, size=12, direction='asc', field='createdTime') =>
    request.get('/comment/byProjectId', {params: {projectId, page, size, direction, field}}),
  comment: (projectId, parentId, replyId, content) =>
    request.post('/comment/', {projectId, parentId, replyId, content})
}

const Category = {
  all: () => 
    request.get('/category/all'),
}

const News = {
  hot: () =>
    request.get('/news/hot'),
}

const Transaction = {
  donate: (projectId, amount) =>
    request.post('/transaction/donation', {projectId, amount}),
  donation: (userId, page, size) =>
    request.get('/transaction/donation', {params: {userId,page, size}})
}

const Auth = {
  login: (loginForm) => 
    request.post('/auth/login', loginForm),
  sign: (signForm) =>
    request.post('/user/sign', signForm)
  
}

const Upload = {
  upload: (formData) => 
    request.request({
      url: '/upload',
      method: 'post',
      headers: {'Content-Type': 'multipart/form-data'},
      data: formData
    })
}

const User = {
  current: () => 
    request.get('/user/current'),
  update: (newUser) =>
    request.put('/user/', newUser)
}

const Banner = {
  all: () => 
    request.get('/banner/all')
}


export default {
  User,
  Auth,
  Category,
  Project,
  Comment,
  News,
  Transaction,
  Upload,
  Banner,
};
