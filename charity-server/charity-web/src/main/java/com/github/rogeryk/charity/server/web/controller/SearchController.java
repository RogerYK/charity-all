package com.github.rogeryk.charity.server.web.controller;

import com.github.rogeryk.charity.server.db.domain.News;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.web.service.SearchService;
import com.github.rogeryk.charity.server.web.utils.Response;

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
