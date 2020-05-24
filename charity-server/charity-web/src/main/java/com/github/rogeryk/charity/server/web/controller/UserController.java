package com.github.rogeryk.charity.server.web.controller;

import com.github.rogeryk.charity.server.core.aop.login.LoginedUser;
import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.service.UserService;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;
import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.UserInfo;
import com.github.rogeryk.charity.server.web.controller.form.*;
import com.github.rogeryk.charity.server.web.service.IdentificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@Slf4j
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IdentificationService identificationService;

    @PostMapping("/sign")
    public Response sign(@RequestBody @Validated SignForm form) {
        User user = userService.findUserByPhoneNumber(form.getPhoneNumber());
        if (user != null) {
            throw new ServiceException(ErrorCodes.USER_EXIST, "用户已存在");
        }
        userService.registerUser(form.getPhoneNumber(), form.getPassword());
        return Response.ok("注册成功");
    }

    @GetMapping("/")
    public Response getUserInfo(@LoginedUser Long userId,  @NotNull Long id) {
        UserInfo info = userService.getUserInfo(id, userId);
        return Response.ok(info);
    }

    @GetMapping("/current")
    public Response getCurrentUserInfo(@LoginedUser Long userId) {
        UserInfo info = userService.getUserInfo(userId);
        return Response.ok(info);
    }

    @PutMapping("/update")
    public Response updateUserInfo(@LoginedUser Long userId, @RequestBody UserForm form) {
        User user = userService.findById(userId);
        form.merge(user);

        userService.saveUser(user);
        return Response.ok(null);
    }

    @PostMapping("/follow/project")
    public Response followProject(@LoginedUser Long userId, @RequestBody FollowProjectForm form) {
        User user = userService.findById(userId);
        if (form.isFollow()) {
            Project project = new Project();
            project.setId(form.getProjectId());
            user.getFollowProjects().add(project);
        } else {
            List<Project> projectList = user.getFollowProjects().stream()
                    .filter(project -> !project.getId().equals(form.getProjectId()))
                    .collect(Collectors.toList());
            user.setFollowProjects(projectList);
        }
        userService.saveUser(user);
        return Response.ok();
    }

    @PostMapping("/follow/user")
    public Response followUser(@NotNull @LoginedUser Long userId, @RequestBody FollowUserForm form) {
        User user = userService.findById(userId);
        if (form.isFollow()) {
            User followUser = userService.findById(form.getUserId());
            user.getFollowedUsers().add(followUser);
        } else {
            List<User> userList = user.getFollowedUsers().stream()
                    .filter(u -> !u.getId().equals(form.getUserId()))
                    .collect(Collectors.toList());
            user.setFollowedUsers(userList);
        }
        userService.saveUser(user);
        return Response.ok();
    }

    @PostMapping("/identify")
    public Response submitIdentification(@LoginedUser @NotNull Long userId, @RequestBody IdentificationForm form) {
        identificationService.saveIdentification(userId, form);
        return Response.ok();
    }
}
