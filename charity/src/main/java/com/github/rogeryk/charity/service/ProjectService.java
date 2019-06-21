package com.github.rogeryk.charity.service;

import com.github.rogeryk.charity.bumo.BumoService;
import com.github.rogeryk.charity.domain.Category;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.ProjectSchedule;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.domain.vo.ProjectDetailVO;
import com.github.rogeryk.charity.exception.ServiceException;
import com.github.rogeryk.charity.repository.CategoryRepository;
import com.github.rogeryk.charity.repository.ProjectRepository;
import com.github.rogeryk.charity.repository.ProjectScheduleRepository;
import com.github.rogeryk.charity.repository.UserRepository;
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

import io.bumo.model.response.result.AccountCreateResult;

@Service
@CacheConfig(cacheNames = "project")
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProjectScheduleRepository projectScheduleRepository;

    @Autowired
    private BumoService bumoService;

    @Autowired
    private UserRepository userRepository;


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
        return PageData.of(projectRepository.findAllByFollowedUsersContaining(user, pageable));
    }

    public PageData<Project> findUserReleaseProjects(User user, Pageable pageable ) {
        return PageData.of(projectRepository.findAllByAuthor(user, pageable));
    }

    public void addSchedule(ProjectSchedule schedule) {
        projectScheduleRepository.save(schedule);
    }


    public ProjectDetailVO findProjectVoByIdAndUserId(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->ServiceException.of(ErrorCodes.PROJECT_NOT_EXIST, "项目不存在"));
        boolean followed = false;
        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));
            followed = projectRepository.existsByIdEqualsAndFollowedUsersContaining(projectId, user);
        }
        return ProjectDetailVO.valueOf(project, followed);
    }

    @CacheEvict(allEntries = true)
    public void save(Project project) {
        if (project.getId() == null) {
            AccountCreateResult account = bumoService.createActiveAccount();
            project.setBumoAddress(account.getAddress());
            project.setBumoPrivateKey(account.getPrivateKey());
        }
        projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }


}
