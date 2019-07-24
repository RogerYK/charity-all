package com.github.rogeryk.charity.server.web.admain.service;

import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.db.domain.Transaction;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public PageData<Transaction> list(Long transactionId, PageParam pageParam) {
        Transaction transaction = new Transaction();
        transaction.setCreatedTime(null);
        if (transactionId != null) {
            transaction.setId(transactionId);
        }
        Example<Transaction> example = Example.of(transaction);
        return PageData.of(transactionRepository.findAll(example ,pageParam.toPageable()));
    }

}
