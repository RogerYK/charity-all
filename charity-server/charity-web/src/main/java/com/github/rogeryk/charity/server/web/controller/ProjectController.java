package com.github.rogeryk.charity.server.web.controller;

import com.github.rogeryk.charity.server.core.aop.login.LoginedUser;
import com.github.rogeryk.charity.server.core.exception.ServiceExceptions;
import com.github.rogeryk.charity.server.core.service.UserService;
import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.ProjectSchedule;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.domain.vo.ProjectDetailVO;
import com.github.rogeryk.charity.server.web.controller.form.ProjectForm;
import com.github.rogeryk.charity.server.web.controller.form.ProjectScheduleForm;
import com.github.rogeryk.charity.server.web.service.ProjectService;
import com.github.rogeryk.charity.server.web.service.RecommendProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

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
    public Response byId(@LoginedUser Long userId, @NotNull Long id) {
        ProjectDetailVO project = projectService.detail(id, userId);
        return Response.ok(project);
    }

    @PostMapping("/")
    public Response save(@LoginedUser User user, @RequestBody ProjectForm form) {
        if (!user.getIdentifyStatus().equals(User.IdentifyStatus.Identified)) {
            throw ServiceExceptions.USER_NO_IDENTIFIED;
        }
        Project project = null;
        if (form.getId() == null) {
            project = new Project();
            project.setAuthor(user);
        } else {
            project = projectService.getProject(form.getId());
        }

        form.merge(project);
        log.info("save project" + form.toString());

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
