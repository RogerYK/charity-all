package com.github.rogeryk.charity.server.web.controller;

import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.UserInfo;
import com.github.rogeryk.charity.server.web.aop.login.LoginedUser;
import com.github.rogeryk.charity.server.web.controller.form.FollowProjectForm;
import com.github.rogeryk.charity.server.web.controller.form.SignForm;
import com.github.rogeryk.charity.server.web.controller.form.UserForm;
import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.web.service.UserService;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;
import com.github.rogeryk.charity.server.web.utils.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@Slf4j
@Validated
public class UserController {

    @Autowired
    private UserService userService;


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
    public Response getUserInfo(@NotNull Long id) {
        UserInfo info;
        info = userService.getUserInfo(id);
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
        Project project = new Project();
        project.setId(form.getProjectId());
        user.getFollowProjects().add(project);
        userService.saveUser(user);
        return Response.ok();
    }

}
