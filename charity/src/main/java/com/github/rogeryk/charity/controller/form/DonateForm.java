package com.github.rogeryk.charity.controller.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DonateForm {

    private Long projectId;


    private BigDecimal amount;
}
