package com.github.rogeryk.charity.server.web.admain.controller;

import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.db.domain.News;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.web.admain.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/news")
public class NewsController {

    @Autowired
    private NewsService newsService;


    @GetMapping("/list")
    public Response list(Long newsId, PageParam pageParam) {
        return Response.ok(newsService.list(newsId,pageParam));
    }
}
