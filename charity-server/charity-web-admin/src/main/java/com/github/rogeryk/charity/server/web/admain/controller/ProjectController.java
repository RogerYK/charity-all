package com.github.rogeryk.charity.server.web.admain.controller;

import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.web.admain.controller.form.ProjectIdForm;
import com.github.rogeryk.charity.server.web.admain.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/admin/project")
@Validated
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/list")
    public Response list(Long projectId, Long authorId, Long categoryId,
                         PageParam pageParam) {
        return Response.ok(projectService.list(projectId, authorId, categoryId, pageParam));
    }

    @PostMapping("/allow")
    public Response allow(@Validated @RequestBody ProjectIdForm form) {
        projectService.allow(form.getId());
        return Response.ok();
    }

    @PostMapping("/delete")
    public Response delete(@Validated @RequestBody ProjectIdForm form) {
        projectService.delete(form.getId());
        return Response.ok();
    }

}
