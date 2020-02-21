package com.github.rogeryk.charity.server.web.admain.controller.form;

import lombok.Data;

import java.util.Date;

@Data
public class UserCountAnalysisParams {
    private Date startTime;
    private Date endTime;
}
