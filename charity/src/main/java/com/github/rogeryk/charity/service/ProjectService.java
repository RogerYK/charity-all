package com.github.rogeryk.charity.service;

import com.github.rogeryk.charity.domain.Category;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.exception.ServiceException;
import com.github.rogeryk.charity.repository.CategoryRepository;
import com.github.rogeryk.charity.repository.ProjectRepository;
import com.github.rogeryk.charity.utils.ErrorCodes;
import com.github.rogeryk.charity.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "project")
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Cacheable(key = "'hotProject('+#p0+')'")
    public List<Project> getTopHotProjects(int n) {
        Pageable pageable = PageRequest.of(0, n,
                Sort.by(Sort.Direction.DESC, "donorCount"));
        return projectRepository.findAll(pageable).getContent();
    }

    @Cacheable
    public PageData<Project> getProjectByCategory(Long id, Pageable pageable) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCodes.CATEGORY_NOT_EXIST, "分类不存在"));
        return PageData.of(projectRepository.findByCategory(category, pageable));
    }

    @Cacheable
    public PageData<Project> findProjectByNameLike(String name, Pageable pageable) {
        name = "%" + name + "%";
        return PageData.of(projectRepository.findAllByNameLike(name, pageable));

    }

    @Cacheable
    public Project getProject(Long id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCodes.PROJECT_NOT_EXIST, "项目不存在"));
    }

    public PageData<Project> findUserFavorProjects(User user, Pageable pageable) {
        return PageData.of(projectRepository.findAllByFavorUsersContains(user, pageable));
    }

    public PageData<Project> findUserReleaseProjects(User user, Pageable pageable ) {
        return PageData.of(projectRepository.findAllByAuthor(user, pageable));
    }


    @CacheEvict(allEntries = true)
    public void save(Project project) {
        projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

}
