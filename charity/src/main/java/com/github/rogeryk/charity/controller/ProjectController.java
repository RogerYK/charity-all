package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.controller.form.ProjectForm;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.service.ProjectService;
import com.github.rogeryk.charity.service.RecommendProjectService;
import com.github.rogeryk.charity.service.UserService;
import com.github.rogeryk.charity.utils.Response;
import com.github.rogeryk.charity.utils.PageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
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
    public Response getProject(Long id) {
        Project project = projectService.getProject(id);
        if (project == null) {
            return Response.error(1, "错误");
        }
        return Response.ok(project);
    }

    @PostMapping("/save")
    public Response saveProject(@RequestBody ProjectForm form) {
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
            if (project == null) {
                return Response.error(1, "项目不存在");
            }
        }

        form.merge(project);

        projectService.save(project);

        return Response.ok(null);
    }


    @GetMapping("/byName")
    public Response searchByName(@NotBlank String name,
                                 @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "12") Integer size,
                                 @RequestParam(defaultValue = "createTime") String field,
                                 @RequestParam(defaultValue = "DESC") String direction) {
        Page<Project> projects = projectService.findProjectByNameLike(name,
                page,
                size,
                field,
                direction);
        return Response.ok(PageData.of(projects));
    }


    @GetMapping("/byCategory")
    public Response getProjectByCategory(@NotNull Long categoryId,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "12") Integer size,
                                         @RequestParam(defaultValue = "createTime") String sortField,
                                         @RequestParam(defaultValue = "DESC") String sortDirection) {
        Page<Project> projectPage = null;
        try {
            projectPage = projectService.getProjectByCategory(categoryId,
                    page,
                    size,
                    sortField,
                    sortDirection);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(1, "错误");
        }
        return Response.ok(PageData.of(projectPage));
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
