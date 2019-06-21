package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.domain.News;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.service.SearchService;
import com.github.rogeryk.charity.utils.PageData;
import com.github.rogeryk.charity.utils.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/search")
@Validated
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/project")
    public Response searchProject(@NotBlank String keyword,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        PageData<Project> pageData = searchService.searchProject(keyword, page, size);
        return Response.ok(pageData);
    }

    @GetMapping("/user")
    public Response searchUser(@NotBlank String keyword,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size) {
        PageData<User> pageData = searchService.searchUser(keyword, page, size);
        return Response.ok(pageData);
    }

    @GetMapping("/news")
    public Response searchNews(@NotBlank String keyword,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size) {
        PageData<News> pageData = searchService.searchNews(keyword, page, size);
        return Response.ok(pageData);
    }
}
