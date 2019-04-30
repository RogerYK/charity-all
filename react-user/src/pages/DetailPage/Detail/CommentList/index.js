import React, { Component } from "react";

import styles from "./style.module.scss";
import { Comment, Avatar, List, Tooltip, Pagination } from "antd";
import { observer, inject } from "mobx-react";
import moment from "moment";
import { CommentStore } from "../../../../store/commentStore";
import { Editor, EditorLine } from "./Editor";
import {withRouter} from 'react-router-dom'
import IconFont from "../../../../components/IconFont";


@inject("userStore")
@observer
class CommentList extends Component {
  constructor(props) {
    super(props);
    this.commentStore = new CommentStore(props.projectId);
  }

  replyComment = (parentId, replyId) => {
    if (!this.props.userStore.logined) {
      this.props.history.push('/login')
    }
    const setParentAndReply = this.commentStore.setParentAndReply;
    setParentAndReply(parentId, replyId);
  };

  getCommentList(comments, parentId, showAll, onShowAll) {
    if (!comments || comments.length === 0) return null
    if (!showAll) {
      comments = comments.slice(0, 3)
    }
    const {
      submitting,
      handleSubmit,
      replyId
    } = this.commentStore;
    return (
        <List
          className={styles['sub-comment-list']}
          itemLayout="horizontal"
          loadMore={showAll ? null : <div className={styles['load-more-wrap']}><span className={styles['load-more']} onClick={onShowAll}>全部评论<IconFont type="icon-sanjiao1" /></span></div>}
          dataSource={comments}
          renderItem={comment => (
            <Comment
              actions={[
                <span className={styles['comment-action']}>
                  <IconFont type="icon-zan" />
                </span>,
                <span className={styles['comment-action']} onClick={() => this.replyComment(parentId, comment.id)}>
                  <IconFont type="icon-pinglun" />
                </span>
              ]}
              author={comment.commenter.nickName}
              avatar={comment.commenter.avatar}
              content={comment.content}
              datetime={
                <Tooltip title={comment.createdTime}>
                  <span>{moment(comment.createdTime, 'YYYY-MM-DD HH:mm:ss').fromNow()}</span>
                </Tooltip>
              }
            >
              {replyId === comment.id ? <EditorLine submitting={submitting} onSubmit={handleSubmit} /> : null}
            </Comment>
          )}
        />
    )
  }

  render() {
    let user = this.props.userStore.currentUser;
    const logined = this.props.userStore.logined;
    const {
      comments,
      subShowFlags,
      total,
      page,
      setPage,
      submitting,
      handleSubmit,
      replyId
    } = this.commentStore;

    console.log(`subShowFlags:${subShowFlags}`)

    return (
      <div>
        <Comment
          avatar={
            user ? (
              <Avatar src={user.avatar} alt={user.nickName} />
            ) : (
              <Avatar icon="user" />
            )
          }
          content={
            <Editor
              onFocus={() => this.replyComment(null, null)}
              logined={logined}
              submitting={submitting}
              onSubmit={handleSubmit}
            /> 
          }
        />
        <List
          itemLayout="horizontal"
          dataSource={comments}
          locale={{emptyText: "没有评论"}}
          renderItem={(comment, i) => (
            <Comment
              actions={[
                <span className={styles['comment-action']}>
                  <IconFont type="icon-zan" />
                </span>,
                <span className={styles['comment-action']} onClick={() => this.replyComment(comment.id, comment.id)}>
                  <IconFont type="icon-pinglun" />
                </span>
              ]}
              author={comment.commenter.nickName}
              avatar={comment.commenter.avatar}
              content={comment.content}
              datetime={
                <Tooltip title={comment.createdTime}>
                  <span>{moment(comment.createdTime, 'YYYY-MM-DD HH:mm:ss').fromNow()}</span>
                </Tooltip>
              }
            >
              {replyId === comment.id ? <EditorLine submitting={submitting} onSubmit={handleSubmit} /> : null}
              {this.getCommentList(comment.subComments, comment.id, subShowFlags[i], ()=> {
                subShowFlags[i] = true
              })}
            </Comment>
          )}
        />
        {total > 10 ? <Pagination
          className={styles["pagination"]}
          current={page + 1}
          onChange={page => setPage(page - 1)}
          defaultCurrent={1}
          pageSize={10}
          total={total}
        /> : null}
      </div>
    );
  }
}

export default withRouter(CommentList)