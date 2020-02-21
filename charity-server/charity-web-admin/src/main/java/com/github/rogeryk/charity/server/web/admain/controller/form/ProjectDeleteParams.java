package com.github.rogeryk.charity.server.web.admain.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProjectDeleteParams {
    @NotNull
    private List<Long> ids;

}
