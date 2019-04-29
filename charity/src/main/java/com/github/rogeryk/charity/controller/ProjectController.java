package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.controller.form.PageParam;
import com.github.rogeryk.charity.controller.form.ProjectForm;
import com.github.rogeryk.charity.controller.form.ProjectScheduleForm;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.ProjectSchedule;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.service.ProjectService;
import com.github.rogeryk.charity.service.RecommendProjectService;
import com.github.rogeryk.charity.service.UserService;
import com.github.rogeryk.charity.utils.PageData;
import com.github.rogeryk.charity.utils.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/project")
@Slf4j
@Validated
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RecommendProjectService recommendProjectService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public Response byId(@NotNull Long id) {
        Project project = projectService.getProject(id);
        return Response.ok(project);
    }

    @PostMapping("/")
    public Response save(@RequestBody ProjectForm form) {
        Project project = null;
        if (form.getId() == null) {
            String phoneNumber = (String) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            User user = userService.findUserByPhoneNumber(phoneNumber);
            project = new Project();
            project.setAuthor(user);
        } else {
            project = projectService.getProject(form.getId());
        }

        form.merge(project);

        projectService.save(project);

        return Response.ok(null);
    }

    @PostMapping("/schedule")
    public Response addSchedule(@Validated @RequestBody ProjectScheduleForm form) {
        ProjectSchedule schedule = new ProjectSchedule();
        schedule.setContent(form.getContent());

        Project project = new Project();
        project.setId(form.getProjectId());
        schedule.setProject(project);

        projectService.addSchedule(schedule);
        return Response.ok();
    }


    @GetMapping("/byName")
    public Response searchByName(@NotBlank String name, PageParam pageParam) {
        PageData<Project> projects = projectService.findProjectByNameLike(name, pageParam.toPageable());
        return Response.ok(projects);
    }

    @GetMapping("/byCategory")
    public Response getProjectByCategory(@NotNull Long categoryId, PageParam pageParam) {
        PageData<Project> projectPage = projectService.getProjectByCategory(categoryId, pageParam.toPageable());
        return Response.ok(projectPage);
    }

    @GetMapping("/favoritedBy")
    public Response favoritedBy(@NotNull Long userId, PageParam pageParam) {
        User user = userService.findById(userId);
        PageData<Project> projectPage = projectService.findUserFavorProjects(user, pageParam.toPageable());
        return Response.ok(projectPage);
    }

    @GetMapping("/releasedBy")
    public Response releasedBy(@NotNull Long userId, PageParam pageParam) {
        User user = userService.findById(userId);
        PageData<Project> projectPage = projectService.findUserReleaseProjects(user, pageParam.toPageable());
        return Response.ok(projectPage);
    }

    @GetMapping("/hot")
    public Response getHotProjects(@RequestParam(defaultValue = "5") int n) {
        List<Project> projects = projectService.getTopHotProjects(n);
        return Response.ok(projects);
    }

    @GetMapping("/recommend")
    public Response getRecommendProjects(@RequestParam(defaultValue = "12") int n) {
        List<Project> projects = recommendProjectService.getRecommendProjectsTop(n);
        return Response.ok(projects);
    }

}
