package com.github.rogeryk.charity.service;

import com.github.rogeryk.charity.domain.News;
import com.github.rogeryk.charity.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> getAllActivities() {
        return newsRepository.findAll();
    }
}
