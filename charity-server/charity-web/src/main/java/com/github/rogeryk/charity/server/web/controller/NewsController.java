package com.github.rogeryk.charity.server.web.controller;

import com.github.rogeryk.charity.server.core.aop.login.LoginedUser;
import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.web.controller.form.NewsForm;
import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.web.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/news")
@Validated
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public Response detail(@NotNull Long id) {
        return Response.ok(newsService.detail(id));
    }

    @GetMapping("/hot")
    public Response hot(@RequestParam(defaultValue = "5") int n) {
        return Response.ok(newsService.hotNews(n));
    }

    @GetMapping("/latest")
    public Response latest(@RequestParam(defaultValue = "5") int n) {
        return Response.ok(newsService.latestNews(n));
    }

    @GetMapping("/all")
    public Response all(PageParam pageParam) {
        return Response.ok(newsService.all(pageParam.toPageable()));
    }

    @GetMapping("/byUser")
    public Response byUser(@NotNull Long userId, PageParam pageParam) {
        return Response.ok(newsService.byUser(userId, pageParam.toPageable()));
    }

    @PostMapping("/")
    public Response save(@LoginedUser Long userId, @Validated @RequestBody NewsForm form) {
        newsService.save(form, userId);
        return Response.ok();
    }



}
