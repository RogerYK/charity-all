package com.github.rogeryk.charity.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CommentForm {

    @NotNull
    private Long projectId;

    private Long parentId;

    private Long replyId;

    @NotEmpty
    private String content;
}
