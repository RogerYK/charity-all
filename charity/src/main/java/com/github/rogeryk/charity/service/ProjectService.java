package com.github.rogeryk.charity.service;

import com.github.rogeryk.charity.domain.Category;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.repository.CategoryRepository;
import com.github.rogeryk.charity.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
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

    @Cacheable(key = "'projectByCategory('+#p0+','+#p1+','+#p2+','+#p3+','+#p4+')'")
    public Page<Project> getProjectByCategory(Long id,
                                              Integer page,
                                              Integer limit,
                                              String sortField,
                                              String sortDirection) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(Exception::new);
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, limit, sort);
        return projectRepository.findByCategory(category, pageable);
    }

    @Cacheable(key = "'findByName('+#p0+','+#p1+','+#p2+','+#p3+','+#p4+')'")
    public Page<Project> findProjectByNameLike(String name,
                                           int page,
                                           int size,
                                           String field,
                                           String direction) {
        name = "%" + name + "%";
        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.by(Sort.Direction.valueOf(direction), field));
        return projectRepository.findAllByNameLike(name, pageRequest);
    }

    @Cacheable(key = "'findById('+#p0+')'")
    public Project getProject(Long id) {
        return projectRepository.getOne(id);
    }

    public Page<Project> findUserFavorProjects(User user, Pageable pageable) {
        return projectRepository.findAllByFavorUsersContains(user, pageable);
    }

    public Page<Project> findUserReleaseProjects(User user, Pageable pageable) {
        return projectRepository.findAllByAuthor(user, pageable);
    }


    @CacheEvict(allEntries = true)
    public void save(Project project) {
        projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

}
