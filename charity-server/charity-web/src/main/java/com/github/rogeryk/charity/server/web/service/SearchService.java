package com.github.rogeryk.charity.server.web.service;

import com.github.rogeryk.charity.server.db.domain.News;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.repository.NewsRepository;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;
import com.github.rogeryk.charity.server.db.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserRepository userRepository;

    public PageData<Project> searchProject(String text, int page, int size) {
        return projectRepository.searchProject(text, page, size);
    }

    public PageData<User> searchUser(String text, int page, int size) {
        return userRepository.searchUser(text, page, size);
    }

    public PageData<News> searchNews(String text, int page, int size) {
        return newsRepository.searchNews(text, page, size);
    }

}
