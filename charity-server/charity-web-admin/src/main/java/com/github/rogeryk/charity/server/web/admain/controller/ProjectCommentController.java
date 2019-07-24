package com.github.rogeryk.charity.server.web.admain.controller;

import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.web.admain.service.ProjectCommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/project/comment")
@Validated
public class ProjectCommentController {

    @Autowired
    private ProjectCommentService projectCommentService;

    @GetMapping("/list")
    public Response list(Long commentId, Long authorId, Long projectId, PageParam pageParam) {
        return Response.ok(projectCommentService.list(commentId, authorId, projectId, pageParam));
    }
}
