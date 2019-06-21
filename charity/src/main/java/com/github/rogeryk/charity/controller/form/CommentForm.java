package com.github.rogeryk.charity.controller.form;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CommentForm {

    private Long projectId;

    private Long newsId;

    private Long parentId;

    private Long replyId;

    @NotEmpty
    private String content;
}
