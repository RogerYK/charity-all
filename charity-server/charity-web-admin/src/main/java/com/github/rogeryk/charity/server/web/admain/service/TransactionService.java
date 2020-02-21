package com.github.rogeryk.charity.server.web.admain.service;

import com.github.rogeryk.charity.server.db.domain.Transaction;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.repository.TransactionRepository;
import com.github.rogeryk.charity.server.web.admain.controller.form.TransactionListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public PageData<Transaction> list(TransactionListParams params) {
        Specification<Transaction> specification = (Specification<Transaction>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (params.getId() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("id"), params.getId()));
            }
            if (!StringUtils.isEmpty(params.getHash())) {
                predicateList.add(criteriaBuilder.equal(root.get("hash"), params.getHash()));
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            predicateList.toArray(predicates);
            return criteriaBuilder.and(predicates);
        };
        return PageData.of(transactionRepository.findAll(specification, params.getPageParam().toPageable()));
    }

}
