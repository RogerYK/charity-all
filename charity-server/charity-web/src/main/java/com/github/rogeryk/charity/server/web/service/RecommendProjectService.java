package com.github.rogeryk.charity.server.web.service;

import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.RecommendProject;
import com.github.rogeryk.charity.server.db.repository.RecommendProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "recommendProject")
public class RecommendProjectService {

    @Autowired
    private RecommendProjectRepository recommendProjectRepository;

    public List<Project> getRecommendProjects() {
        return recommendProjectRepository.findAll().stream().map(RecommendProject::getProject).collect(Collectors.toList());
    }

    @Cacheable(key = "'topRecommend('+#p0+')'")
    public List<Project> getRecommendProjectsTop(int n) {
        return recommendProjectRepository.findTop(n).stream().map(RecommendProject::getProject).collect(Collectors.toList());
    }

    public void addRecommendProject(Long projectId) {
        RecommendProject recommendProject = new RecommendProject();
        Project project = new Project();
        project.setId(projectId);
        recommendProject.setProject(project);
        recommendProjectRepository.save(recommendProject);
    }
}
