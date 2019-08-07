package com.github.rogeryk.charity.server.web.service;

import com.github.rogeryk.charity.server.core.search.index.ProjectDocument;
import com.github.rogeryk.charity.server.core.search.repository.ProjectDocumentRepository;
import com.github.rogeryk.charity.server.db.domain.Category;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.ProjectSchedule;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.domain.vo.ProjectDetailVO;
import com.github.rogeryk.charity.server.db.repository.CategoryRepository;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;
import com.github.rogeryk.charity.server.db.repository.ProjectScheduleRepository;
import com.github.rogeryk.charity.server.db.repository.UserRepository;
import com.github.rogeryk.charity.server.core.bumo.BumoService;
import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import io.bumo.model.response.result.AccountCreateResult;
import lombok.extern.slf4j.Slf4j;

@Service
@CacheConfig(cacheNames = "project")
@Slf4j
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
    @Autowired
    private ProjectDocumentRepository projectDocumentRepository;



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


    public ProjectDetailVO detail(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()->ServiceException.of(ErrorCodes.PROJECT_NOT_EXIST, "项目不存在"));
        boolean followed = false;
        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));
            followed = projectRepository.existsByIdEqualsAndFollowedUsersContaining(projectId, user);
        }
        projectRepository.incrementNewsWatchCount(projectId);
        return ProjectDetailVO.valueOf(project, followed);
    }

    @CacheEvict(allEntries = true)
    public void save(Project project) {
        if (project.getId() == null) {
            AccountCreateResult account = bumoService.createActiveAccount();
            project.setBumoAddress(account.getAddress());
            project.setBumoPrivateKey(account.getPrivateKey());
        }
        project = projectRepository.saveAndFlush(project);
        ProjectDocument document = ProjectDocument.create(project);
        projectDocumentRepository.save(document);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }


}
