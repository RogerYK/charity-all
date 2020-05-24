package com.github.rogeryk.charity.server.web.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FollowUserForm {
    @NotNull
    private Long userId;
    private boolean follow;
}
