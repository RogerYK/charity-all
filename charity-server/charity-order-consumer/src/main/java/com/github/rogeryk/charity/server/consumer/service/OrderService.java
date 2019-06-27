package com.github.rogeryk.charity.server.consumer.service;

import com.github.rogeryk.charity.server.core.bumo.BumoService;
import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.mq_data.OrderEvent;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.Transaction;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;
import com.github.rogeryk.charity.server.db.repository.TransactionRepository;
import com.github.rogeryk.charity.server.db.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BumoService bumoService;

    //验证消息是否已经处理，处理过就直接返回
    public boolean isProcessed(OrderEvent event) {
        Optional<Transaction> old = transactionRepository.findByUniqueId(event.getUniqueId());
        return old.isPresent();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void donate(OrderEvent event) {
        Project project = projectRepository.findById(event.getAnotherId()).orElseThrow(RuntimeException::new);
        User user = userRepository.
                findById(event.getUserId())
                .orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));

        if (user.getMoney().compareTo(event.getAmount()) < 0) {
            throw new ServiceException(ErrorCodes.USER_NO_MONEY, "用户余额不足");
        }
        user.setMoney(user.getMoney().subtract(event.getAmount()));
        userRepository.save(user);

        project.setRaisedMoney(project.getRaisedMoney().add(event.getAmount()));
        project.setDonorCount(project.getDonorCount()+1);
        projectRepository.save(project);

        Transaction ts = new Transaction();

        ts.setPayer(user);
        ts.setMoney(event.getAmount());
        ts.setProject(project);
        ts.setType(Transaction.TransactionType.Donation);
        ts.setUniqueId(event.getUniqueId());

        transactionRepository.save(ts);

        String hash = bumoService.assetSend(user.getBumoAddress(), project.getBumoAddress(), user.getBumoPrivateKey(), event.getAmount().longValue());

        ts.setHash(hash);
        transactionRepository.save(ts);
    }

    @Transactional
    public void recharge(OrderEvent event) {
        // 刷新用户数据
        User user = userRepository
                .findById(event.getUserId())
                .orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));
        user.setMoney(user.getMoney().add(event.getAmount()));
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setPayee(user);
        transaction.setType(Transaction.TransactionType.Recharge);
        transaction.setMoney(event.getAmount());
        transaction.setUniqueId(event.getUniqueId());
        transactionRepository.save(transaction);

        String hash = bumoService.recharge(user.getBumoAddress(), event.getAmount().longValue());

        transaction.setHash(hash);
        transactionRepository.save(transaction);
    }


}

