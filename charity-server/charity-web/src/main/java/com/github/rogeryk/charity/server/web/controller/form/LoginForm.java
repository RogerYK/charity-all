package com.github.rogeryk.charity.server.web.controller.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginForm {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;
}
