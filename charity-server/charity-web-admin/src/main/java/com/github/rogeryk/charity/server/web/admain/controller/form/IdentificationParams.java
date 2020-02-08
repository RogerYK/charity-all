package com.github.rogeryk.charity.server.web.admain.controller.form;

import com.github.rogeryk.charity.server.core.util.PageParam;
import lombok.Data;

import java.util.List;

@Data
public class IdentificationParams  {
    private List<String> states;
    private Long beginTime;
    private Long endTime;
    private PageParam pageParam;
}
