package com.github.rogeryk.charity.server.web.admain.controller.form;

import lombok.Data;

import java.util.List;

@Data
public class IdListParams {
    private List<Long> ids;
}
