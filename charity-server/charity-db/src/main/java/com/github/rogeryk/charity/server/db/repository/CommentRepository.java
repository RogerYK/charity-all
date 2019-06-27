package com.github.rogeryk.charity.server.db.repository;

import com.github.rogeryk.charity.server.db.domain.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByProjectIdAndParentCommentId(Long projectId, Long parentId,  Pageable pageable);
}
