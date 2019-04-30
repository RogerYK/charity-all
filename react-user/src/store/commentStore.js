import { observable, action, autorun } from 'mobx';
import api from '../api'
import { message } from 'antd';

export class CommentStore {

  @observable projectId

  @observable page = 0

  size = 10

  @observable comments = []

  @observable subShowFlags = []

  @observable total = 0

  @observable parentId

  @observable replyId

  @observable submiting = false

  constructor(projectId) {
    this.projectId = projectId
    autorun(() =>
      this.pullComments()
    )
  }

  @action
  setPage = (page) => {
    console.log(`set page ${page}`)
    this.page = page
  }

  @action
  handleSubmit = (content) => {
    if (!this.submiting) {
      this.submiting = true
      api.Comment.comment(this.projectId, this.parentId, this.replyId, content)
        .then(action(res => {
          this.submiting = false
          message.success('评论成功')
          this.parentId = null
          this.replyId = null
          this.pullComments()
        })).catch(action(res => {
          this.submit = false
          message.error(res.msg)
        }))
    }
  }

  @action 
  setParentAndReply = (parentId, replyId) => {
    this.parentId = parentId
    this.replyId = replyId
  }

  pullComments = () => {
    api.Comment.byProjectId(this.projectId, this.page, this.size)
      .then(action(res => {
        console.log(res)
        this.comments = res.data.content
        this.total = res.data.total
        this.subShowFlags = new Array(this.comments.length).fill(false)
      }))
  }

}

