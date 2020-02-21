package com.github.rogeryk.charity.server.web.admain.controller;

import com.github.rogeryk.charity.server.core.service.UserService;
import com.github.rogeryk.charity.server.core.util.Response;

import com.github.rogeryk.charity.server.web.admain.controller.form.UserListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    public Response list(@Validated @RequestBody UserListParams params) {
        return Response.ok(userService.list(params.getId(), params.getNickName(), params.getPhoneNumber(), params.getPageParam().toPageable()));
    }
}
