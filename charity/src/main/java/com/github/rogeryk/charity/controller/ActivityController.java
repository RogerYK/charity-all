package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.service.ActivityService;
import com.github.rogeryk.charity.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {


    @Autowired
    private ActivityService activityService;

    @GetMapping("/hot")
    public Response getActivities() {
        return Response.ok(activityService.getAllActivities());
    }
}
