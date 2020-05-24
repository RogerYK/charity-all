package com.github.rogeryk.charity.server.web.service.vo;

import com.github.rogeryk.charity.server.db.domain.Comment;
import com.github.rogeryk.charity.server.db.domain.User;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentVO {
    private Long id;

    private String content;


    private User commenter;

    private CommentVO replyComment;

    private List<CommentVO> subComments;

    private Date createdTime;

    private Boolean favored;


    public static CommentVO from(Comment comment, Long userId) {
        if (comment == null) return null;
        CommentVO vo = new CommentVO();
        vo.setId(comment.getId());
        vo.setContent(comment.getContent());
        vo.setCommenter(comment.getCommenter());
        vo.setReplyComment(replyFrom(comment.getReplyComment(), userId));
        vo.setSubComments(comment.getSubComments()
                .stream()
                .map(cmt -> CommentVO.from(cmt, userId))
                .collect(Collectors.toList())
        );
        vo.setCreatedTime(comment.getCreatedTime());
        vo.setFavored(comment.getFavorUsers().stream().anyMatch(user -> user.getId().equals(userId)));
        return vo;
    }

    public static CommentVO replyFrom(Comment comment, Long userId) {
        if (comment == null) return null;
        CommentVO vo = new CommentVO();
        vo.setId(comment.getId());
        vo.setCommenter(comment.getCommenter());
        return vo;
    }

    public static List<CommentVO> from(List<Comment> comments, Long userId) {
        return comments.stream()
                .map(comment -> from(comment, userId))
                .collect(Collectors.toList());
    }
}
