package com.github.rogeryk.charity.server.web.admain.controller;

import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.web.admain.controller.form.ProjectDeleteParams;
import com.github.rogeryk.charity.server.web.admain.controller.form.ProjectIdForm;
import com.github.rogeryk.charity.server.web.admain.controller.form.ProjectListForm;
import com.github.rogeryk.charity.server.web.admain.service.ProjectService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/projects")
@Validated
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping("/list")
    public Response list(@Validated @RequestBody ProjectListForm form) {
        log.debug("project list params {}", form);
        return Response.ok(projectService.list(form));
    }

    @PostMapping("/allow")
    public Response allow(@Validated @RequestBody ProjectIdForm form) {
        projectService.allow(form.getId());
        return Response.ok();
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody ProjectDeleteParams params) {
        projectService.deleteAll(params.getIds());
        return Response.ok();
    }

}
