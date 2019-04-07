package com.github.rogeryk.charity.service;

import com.github.rogeryk.charity.domain.Comment;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.repository.CommentRepository;
import com.github.rogeryk.charity.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    ProjectRepository projectRepository;

    public void coment(User user, Long projectId, String content, Long lastCommentId) {
        Comment comment = new Comment();
        comment.setProject(projectRepository.getOne(projectId));
        comment.setContent(content);
        comment.setLastComment(commentRepository.getOne(lastCommentId));
        comment.setCommenter(user);
        commentRepository.save(comment);
    }

}
