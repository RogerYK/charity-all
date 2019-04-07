package com.github.rogeryk.charity.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;
}
