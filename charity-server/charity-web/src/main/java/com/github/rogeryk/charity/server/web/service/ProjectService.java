package com.github.rogeryk.charity.server.web.service;

import com.github.rogeryk.charity.server.core.bumo.BumoService;
import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
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

    public List<Project> getTopHotProjects(int n) {
        Pageable pageable = PageRequest.of(0, n,
                Sort.by(Sort.Direction.DESC, "donorCount", "watchCount"));
        return projectRepository.findAllByStatusIn(
                    Project.ProjectStatus.userViewStatus,
                    pageable
            ).getContent();
    }

    public PageData<Project> getProjectByCategory(Long id, Pageable pageable) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCodes.CATEGORY_NOT_EXIST, "分类不存在"));
        return PageData.of(projectRepository.findByCategoryAndStatusIn(category,
                Project.ProjectStatus.userViewStatus,
                pageable));
    }

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

    @Transactional
    public void save(Project project) {
        boolean isNew = project.getId() == null;
        project = projectRepository.saveAndFlush(project);
        if (isNew) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Date endTime = project.getEndTime();
            long deadline = endTime.getTime() * 1000;           //转为16位的微秒时间戳
            long target = project.getTargetMoney().longValue();
            String hash = bumoService.createContract(user.getBumoAddress(), target, deadline);

            project.setTransactionHash(hash);
            project.setTransactionNumber(0);
            project.setStatus(Project.ProjectStatus.Creating);
            projectRepository.saveAndFlush(project);
        }
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

}
