package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.aop.login.LoginedUser;
import com.github.rogeryk.charity.controller.form.SignForm;
import com.github.rogeryk.charity.controller.form.UserForm;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.domain.form.UserInfo;
import com.github.rogeryk.charity.exception.ServiceException;
import com.github.rogeryk.charity.service.UserService;
import com.github.rogeryk.charity.utils.ErrorCodes;
import com.github.rogeryk.charity.utils.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public Response getCurrentUserInfo(@LoginedUser @NotNull User user) {
        log.info("currentUser: " + user.getPhoneNumber());
        UserInfo info = userService.getUserInfo(user.getPhoneNumber());
        return Response.ok(info);
    }

    @PutMapping("/update")
    public Response updateUserInfo(HttpServletRequest request, @RequestBody UserForm form) {
        String phoneNumber = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userService.findUserByPhoneNumber(phoneNumber);
        form.merge(user);

        userService.saveUser(user);
        return Response.ok(null);
    }

}
