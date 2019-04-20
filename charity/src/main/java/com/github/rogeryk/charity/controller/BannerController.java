package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.domain.Banner;
import com.github.rogeryk.charity.repository.BannerRepository;
import com.github.rogeryk.charity.utils.Response;
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
