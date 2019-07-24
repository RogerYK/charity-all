package com.github.rogeryk.charity.server.web.admain.controller;

import com.github.rogeryk.charity.server.core.service.UserService;
import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.core.util.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/ordinary_user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Response list(Long userId, PageParam pageParam) {
        return Response.ok(userService.list(userId, pageParam));
    }
}
