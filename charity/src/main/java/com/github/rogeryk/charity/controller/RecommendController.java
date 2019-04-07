package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.service.RecommendProjectService;
import com.github.rogeryk.charity.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/recommend")
@Validated
@Slf4j
public class RecommendController {

    @Autowired
    private RecommendProjectService service;

    @GetMapping("/")
    public Response getRecommendProjects(@NotNull Integer topN) {
        log.debug("recommend topN="+topN+";");
        return Response.ok(service.getRecommendProjectsTop(topN));
    }
}
