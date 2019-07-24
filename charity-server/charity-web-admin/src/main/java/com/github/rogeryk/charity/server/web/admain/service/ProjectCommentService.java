package com.github.rogeryk.charity.server.web.admain.service;

import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.db.domain.Comment;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ProjectCommentService {

    @Autowired
    private CommentRepository commentRepository;


    public Object list(Long commentId, Long authorId, Long projectId, PageParam pageParam) {
        Comment comment = new Comment();

        if (commentId != null) {
            comment.setId(commentId);
        }
        if (authorId != null) {
            User user = new User();
            user.setId(authorId);
            comment.setCommenter(user);
        }
        if (projectId != null) {
            Project project = new Project();
            project.setId(projectId);
            comment.setProject(project);
        }
        Example<Comment> example = Example.of(comment);

        return PageData.of(commentRepository.findAll(example, pageParam.toPageable()));
    }
}
