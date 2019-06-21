package com.github.rogeryk.charity.service;

import com.github.rogeryk.charity.controller.form.CommentForm;
import com.github.rogeryk.charity.domain.Comment;
import com.github.rogeryk.charity.domain.News;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.exception.ServiceException;
import com.github.rogeryk.charity.repository.CommentRepository;
import com.github.rogeryk.charity.repository.ProjectRepository;
import com.github.rogeryk.charity.repository.UserRepository;
import com.github.rogeryk.charity.utils.ErrorCodes;
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

    @Autowired
    UserRepository userRepository;

    public void save(Long userId, CommentForm form) {
        Comment comment = new Comment();

        User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));

        comment.setCommenter(user);

        if (form.getProjectId() != null) {
            Project project = new Project();
            project.setId(form.getProjectId());
            comment.setProject(project);
        } else if (form.getNewsId() != null) {
            News news = new News();
            news.setId(form.getNewsId());
            comment.setNews(news);
        }

        if (form.getParentId() != null) {
            Comment parent = new Comment();
            parent.setId(form.getParentId());
            comment.setParentComment(parent);
        }

        if (form.getReplyId() != null) {
            Comment reply = new Comment();
            reply.setId(form.getReplyId());
            comment.setReplyComment(reply);
        }

        comment.setContent(form.getContent());

        commentRepository.save(comment);
    }

    public PageData<Comment> findRootCommentByProjectId(Long projectId, Pageable pageable) {
        return PageData.of(commentRepository.findByProjectIdAndParentCommentId(projectId, null, pageable));
    }

}
