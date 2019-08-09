package com.github.rogeryk.charity.server.web.controller;

import com.github.rogeryk.charity.server.core.search.SearchService;
import com.github.rogeryk.charity.server.core.search.index.NewsDocument;
import com.github.rogeryk.charity.server.core.search.index.ProjectDocument;
import com.github.rogeryk.charity.server.core.search.index.UserDocument;
import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.db.domain.News;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/search")
@Validated
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/project")
    public Response searchProject(@NotBlank String keyword,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        PageData<ProjectDocument> pageData = searchService.searchProject(keyword, page, size);
        return Response.ok(pageData);
    }

    @GetMapping("/user")
    public Response searchUser(@NotBlank String keyword,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size) {
        PageData<UserDocument> pageData = searchService.searchUser(keyword, page, size);
        return Response.ok(pageData);
    }

    @GetMapping("/news")
    public Response searchNews(@NotBlank String keyword,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size) {
        PageData<NewsDocument> pageData = searchService.searchNews(keyword, page, size);
        return Response.ok(pageData);
    }

    @PostMapping("/import")
    public Response importDocument() {
        searchService.importDocument();
        return Response.ok();
    }
}
