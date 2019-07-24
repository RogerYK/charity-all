package com.github.rogeryk.charity.server.web.admain.service;

import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.db.domain.Category;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public PageData<Project> list(Long projectId, Long authorId, Long categoryId, PageParam pageParam) {
        Pageable pageable = pageParam.toPageable();
        Project project = new Project();
        project.setCreatedTime(null); //
        if (projectId != null) {
            project.setId(projectId);
        }
        if (authorId != null) {
            User user = new User();
            user.setId(authorId);
            project.setAuthor(user);
        }
        if (categoryId != null) {
            Category category = new Category();
            category.setId(categoryId);
            project.setCategory(category);
        }
        Example<Project> example = Example.of(project);
        return PageData.of(projectRepository.findAll(example, pageable));
    }


    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
}

