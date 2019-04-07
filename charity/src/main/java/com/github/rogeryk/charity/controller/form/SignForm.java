package com.github.rogeryk.charity.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SignForm {

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^\\d{11}$", message = "手机号不正确")
    private String phoneNumber;

    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 30, message = "密码最小6位，最长30位")
    private String password;
}
