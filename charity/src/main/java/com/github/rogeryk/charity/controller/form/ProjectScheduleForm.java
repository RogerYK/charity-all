package com.github.rogeryk.charity.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProjectScheduleForm {

    @NotNull
    private Long projectId;

    @NotEmpty
    private String content;

}
