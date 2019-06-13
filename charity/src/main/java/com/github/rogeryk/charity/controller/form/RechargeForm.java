package com.github.rogeryk.charity.controller.form;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RechargeForm {

    @NotNull
    private Long amount;
}
