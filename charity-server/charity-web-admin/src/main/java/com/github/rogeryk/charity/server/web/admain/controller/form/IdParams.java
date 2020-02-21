package com.github.rogeryk.charity.server.web.admain.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IdParams {
    @NotNull
    private Long id;
}
