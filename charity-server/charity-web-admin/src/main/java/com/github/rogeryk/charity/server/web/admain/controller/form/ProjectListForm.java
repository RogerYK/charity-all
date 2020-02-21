package com.github.rogeryk.charity.server.web.admain.controller.form;

import com.github.rogeryk.charity.server.core.util.PageParam;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectListForm {
    private Long id;
    private String name;
    private Date beginTime;
    private Date endTime;
    private List<String> statusList;
    private PageParam pageParam;
}
