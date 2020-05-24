package com.github.rogeryk.charity.server.web.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentIdForm {
    @NotNull
    private Long commentId;
    private boolean favor;
}
