package com.github.rogeryk.charity.server.web.controller;

import com.github.rogeryk.charity.server.core.aop.login.LoginedUser;
import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.web.controller.form.CommentForm;
import com.github.rogeryk.charity.server.web.controller.form.CommentIdForm;
import com.github.rogeryk.charity.server.web.service.CommentService;
import com.github.rogeryk.charity.server.web.service.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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

    @PostMapping("/favor")
    public Response favor(@LoginedUser Long userId, @Validated @RequestBody CommentIdForm form) {
        commentService.favor(userId, form.getCommentId(), form.isFavor());
        return Response.ok();
    }

    @GetMapping("/byProjectId")
    public Response byProjectId(@LoginedUser Long userId , @NotNull Long projectId, PageParam pageParam) {
        PageData<CommentVO> commentPageData = commentService.findRootCommentByProjectId(userId ,projectId,
                pageParam.toPageable());
        return Response.ok(commentPageData);
    }

}
