package com.github.rogeryk.charity.server.web.controller.form;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DonateForm {

    private Long projectId;


    private BigDecimal amount;
}
