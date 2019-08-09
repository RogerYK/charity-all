package com.github.rogeryk.charity.server.web.admain.service;

import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;
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

    //项目通过检查上线
    public void allow(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() ->ServiceException.of(ErrorCodes.PROJECT_NOT_EXIST, "项目不存在"));
        if (project.getStatus().equals(Project.ProjectStatus.APPLY)) {
            project.setStatus(Project.ProjectStatus.EXAMINATION);
            projectRepository.save(project);
        }
    }


    //只是将项目设为删除状态，使得项目不可见
    public void delete(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> ServiceException.of(ErrorCodes.PROJECT_NOT_EXIST, "项目不能存在"));
        project.setStatus(Project.ProjectStatus.DELETE);
        projectRepository.save(project);
    }
}

