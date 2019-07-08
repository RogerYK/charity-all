package com.github.rogeryk.charity.server.web.controller;

import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.db.domain.Banner;
import com.github.rogeryk.charity.server.db.repository.BannerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Autowired
    private BannerRepository bannerRepository;

    @GetMapping("/all")
    public Response all() {
        List<Banner> bannerList = bannerRepository.findAll();

        return Response.ok(bannerList);
    }
}
