package com.github.rogeryk.charity.server.web.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RechargeForm {

    @NotNull
    private BigDecimal amount;
}
