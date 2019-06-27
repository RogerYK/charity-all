package com.github.rogeryk.charity.server.web.controller.form;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class NewsForm {

    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String introduce;

    private String img;
}
