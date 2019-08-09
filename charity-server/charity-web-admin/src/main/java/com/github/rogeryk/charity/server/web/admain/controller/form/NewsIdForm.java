package com.github.rogeryk.charity.server.web.admain.controller.form;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NewsIdForm {
    @NotNull
    private Long id;
}
