package com.github.rogeryk.charity.server.web.service;

import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;
import com.github.rogeryk.charity.server.db.domain.Comment;
import com.github.rogeryk.charity.server.db.domain.News;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.repository.CommentRepository;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;
import com.github.rogeryk.charity.server.db.repository.UserRepository;
import com.github.rogeryk.charity.server.web.controller.form.CommentForm;
import com.github.rogeryk.charity.server.web.service.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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



    public PageData<CommentVO> findRootCommentByProjectId(Long userId, Long projectId, Pageable pageable) {
        Page<Comment> page = commentRepository.findByProjectIdAndParentCommentId(projectId, null, pageable);
        List<CommentVO> data = CommentVO.from(page.getContent(), userId);
        Long total = page.getTotalElements();
        return PageData.of(total, data);
    }

    public void favor(Long userId, Long commentId, boolean favor) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> ServiceException.of(ErrorCodes.COMMENT_NOT_EXIST, "项目不存在"));
        List<User> favorUsers = comment.getFavorUsers();
        if (favor) {
            if (favorUsers.stream().anyMatch(u -> u.getId().equals(userId))) return; //已经关注了
            User user = userRepository.findById(userId).orElseThrow(() -> ServiceException.of(ErrorCodes.USER_NOT_EXIST, "用户不存在"));
            favorUsers.add(user);
        } else {
            favorUsers = favorUsers.stream().filter(user -> !user.getId().equals(userId)).collect(Collectors.toList());
            comment.setFavorUsers(favorUsers);
        }
        commentRepository.save(comment);
    }
}
