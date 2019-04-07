package com.github.rogeryk.charity.service;

import com.github.rogeryk.charity.domain.Activity;
import com.github.rogeryk.charity.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public List<Activity> getAllActivities() {
        return repository.findAll();
    }
}
