package com.github.rogeryk.charity.service;

import com.github.rogeryk.charity.domain.Comment;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.repository.CommentRepository;
import com.github.rogeryk.charity.repository.ProjectRepository;
import com.github.rogeryk.charity.utils.PageData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    ProjectRepository projectRepository;

    public void save(User user, Long projectId, Long parentId, Long replyId, String content) {
        Comment comment = new Comment();
        comment.setCommenter(user);

        Project project = new Project();
        project.setId(projectId);
        comment.setProject(project);

        if (parentId != null) {
            Comment parent = new Comment();
            parent.setId(parentId);
            comment.setParentComment(parent);
        }

        if (replyId != null) {
            Comment reply = new Comment();
            reply.setId(replyId);
            comment.setReplyComment(reply);
        }

        comment.setContent(content);

        commentRepository.save(comment);
    }

    public PageData<Comment> findRootCommentByProjectId(Long projectId, Pageable pageable) {
        return PageData.of(commentRepository.findByProjectIdAndParentCommentId(projectId, null, pageable));
    }

}
