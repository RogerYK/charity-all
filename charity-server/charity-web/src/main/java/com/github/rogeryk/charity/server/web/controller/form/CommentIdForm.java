package com.github.rogeryk.charity.server.web.controller.form;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CommentIdForm {
    @NotNull
    private Long commentId;
}
