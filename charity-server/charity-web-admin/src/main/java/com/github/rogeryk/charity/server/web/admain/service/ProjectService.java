package com.github.rogeryk.charity.server.web.admain.service;

import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;

import com.github.rogeryk.charity.server.web.admain.controller.form.ProjectListForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public PageData<Project> list(ProjectListForm form) {
        Specification<Project> specification = (Specification<Project>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (form.getId() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("id"), form.getId()));
            }
            if (!StringUtils.isEmpty(form.getName())) {
                predicateList.add(criteriaBuilder.equal(root.get("name"), form.getName()));
            }
            List<String> states = form.getStatusList();
            if (states != null && !states.isEmpty()) {
                CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("status"));
                states.forEach(s -> in.value(Integer.valueOf(s)));
                predicateList.add(in);
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            predicateList.toArray(predicates);
            return criteriaBuilder.and(predicates);
        };
        return PageData.of(projectRepository.findAll(specification, form.getPageParam().toPageable()));
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


    public void deleteAll(List<Long> ids) {
        projectRepository.deleteAll(ids);
    }
}

