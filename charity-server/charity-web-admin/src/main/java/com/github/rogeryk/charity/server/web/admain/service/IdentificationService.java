package com.github.rogeryk.charity.server.web.admain.service;

import com.github.rogeryk.charity.server.core.exception.ServiceExceptions;
import com.github.rogeryk.charity.server.db.domain.Identification;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.repository.IdentificationRepository;
import com.github.rogeryk.charity.server.db.repository.UserRepository;
import com.github.rogeryk.charity.server.web.admain.controller.form.IdentificationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IdentificationService {

    @Autowired
    private IdentificationRepository identificationRepository;

    @Autowired
    private UserRepository userRepository;

    public PageData<Identification> list(IdentificationParams params) {
        Specification<Identification> specification = (Specification<Identification>) (root, criteriaQuery, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (params.getStates() != null && !params.getStates().isEmpty()) {
                CriteriaBuilder.In<Identification.IdentificationState> in = builder.in(root.get("identificationStates"));
                params.getStates().forEach(s -> in.value(Identification.IdentificationState.valueOf(s)));
                predicateList.add(in);
            }
            if (params.getBeginTime() != null) {
                Date beginTime = new Date(params.getBeginTime());
                predicateList.add(builder.greaterThan(root.get("createdTime"), beginTime));
            }
            if (params.getEndTime() != null) {
                Date endTime = new Date(params.getEndTime());
                predicateList.add(builder.lessThanOrEqualTo(root.get("createdTime"), endTime));
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            predicateList.toArray(predicates);
            return builder.and(predicates);
        };
        Page<Identification> identificationPage =  identificationRepository.findAll(specification, params.getPageParam().toPageable());
        return PageData.of(identificationPage);
    }

    @Transactional
    public void pass(Long id) {
        Identification identification = identificationRepository.findById(id)
                .orElseThrow(() -> ServiceExceptions.UNKNOWN_ERROR); //TODO 补充错误码
        identification.setIdentificationState(Identification.IdentificationState.Pass);
        User user = userRepository.findById(identification.getUserId()).orElseThrow(() -> ServiceExceptions.UNKNOWN_ERROR);
        user.setIdentifyStatus(User.IdentifyStatus.Identified);
        userRepository.save(user);
        identificationRepository.save(identification);
    }

    public void  reject(Long id) {
        Identification identification = identificationRepository.findById(id)
                .orElseThrow(() -> ServiceExceptions.UNKNOWN_ERROR); //TODO 补充错误码
        identification.setIdentificationState(Identification.IdentificationState.Reject);
        identificationRepository.save(identification);
    }
}
