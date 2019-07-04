package com.github.rogeryk.charity.server.web.controller;

import com.github.rogeryk.charity.server.db.domain.Comment;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.web.aop.login.LoginedUser;
import com.github.rogeryk.charity.server.web.controller.form.CommentForm;
import com.github.rogeryk.charity.server.web.controller.form.PageParam;
import com.github.rogeryk.charity.server.web.service.CommentService;
import com.github.rogeryk.charity.server.web.utils.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/comment")
@Slf4j
@Validated
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/")
    public Response save(@LoginedUser Long userId, @Validated @RequestBody CommentForm form) {
        commentService.save(userId, form);
        return Response.ok();
    }

    @GetMapping("/byProjectId")
    public Response byProjectId(@NotNull Long projectId, PageParam pageParam) {
        PageData<Comment> commentPageData = commentService.findRootCommentByProjectId(projectId,
                pageParam.toPageable());
        return Response.ok(commentPageData);
    }
}