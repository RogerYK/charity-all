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
import io.bumo.model.response.result.TransactionGetInfoResult;
import io.bumo.model.response.result.data.TransactionHistory;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

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

    //TODO 并发修改问题
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void donate(OrderEvent event) {
        Project project = projectRepository.findById(event.getAnotherId()).orElseThrow(RuntimeException::new);
        User user = userRepository.
                findById(event.getUserId())
                .orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));

        if (user.getMoney().compareTo(event.getAmount()) < 0) {
            throw new ServiceException(ErrorCodes.USER_NO_MONEY, "用户余额不足");
        }

        Transaction ts = transactionRepository.findByUniqueId(event.getUniqueId()).orElseThrow(() -> new RuntimeException("not find transaction "));

        String hash = bumoService.assetSend(user.getBumoAddress(), project.getBumoAddress(), user.getBumoPrivateKey(), event.getAmount().longValue());

        ts.setHash(hash);
        transactionRepository.saveAndFlush(ts);

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderType(OrderEvent.CHECK);
        orderEvent.setUniqueId(event.getUniqueId());
        Message msg = rocketMQTemplate.getMessageConverter().toMessage(orderEvent, null);
        rocketMQTemplate.syncSend("order-event", msg, Integer.MAX_VALUE, 3);
    }

    public void recharge(OrderEvent event) {

        try {
            Transaction ts = transactionRepository.findByUniqueId(event.getUniqueId()).orElseThrow(() -> new RuntimeException("not find transaction "));

            String hash = bumoService.recharge(ts.getPayee().getBumoAddress(), event.getAmount().longValue());

            ts.setHash(hash);
            transactionRepository.save(ts);

            OrderEvent orderEvent = new OrderEvent();
            orderEvent.setOrderType(OrderEvent.CHECK);
            orderEvent.setUniqueId(event.getUniqueId());
            Message msg = rocketMQTemplate.getMessageConverter().toMessage(orderEvent, null);
            rocketMQTemplate.syncSend("order-event", msg, Integer.MAX_VALUE, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Transactional
    public void check(OrderEvent orderEvent) {
        Transaction transaction = transactionRepository.findByUniqueId(orderEvent.getUniqueId()).orElseThrow(() -> new RuntimeException("not find transaction"));
        TransactionGetInfoResult result = bumoService.getTransaction(transaction.getHash());
        if (result.getTotalCount() == 0 || result.getTransactions().length == 0) {
            log.error("not find transaction in block chain");
            throw new RuntimeException("not find transaction in block chain");
        }
        TransactionHistory[] transactionHistories = result.getTransactions();
        TransactionHistory transactionHistory = null;
        for (TransactionHistory history : transactionHistories) {
            if (history.getHash().equals(transaction.getHash())) {
                transactionHistory = history;
                break;
            }
        }
        if (transactionHistory == null) {
            log.error("not find transaction in block chain");
            throw new RuntimeException("not find transaction in block chain");
        }

        if (transactionHistory.getErrorCode() != 0) {
            log.error("transaction error with  error code:{}, error desc{}", transactionHistory.getErrorCode(), transactionHistory.getErrorDesc());
            transaction.setStatus(Transaction.TransactionStatus.Failed);
            return;
        }
        transaction.setStatus(Transaction.TransactionStatus.Success);
        transactionRepository.save(transaction);

        User payer = transaction.getPayer();
        if (payer != null) {
            payer.setMoney(payer.getMoney().subtract(transaction.getMoney()));
            userRepository.save(payer);
        }
        User payee = transaction.getPayee();
        if (payee != null) {
            payee.setMoney(payee.getMoney().add(transaction.getMoney()));
            userRepository.save(payee);
        }
        Project project = transaction.getProject();
        if (project != null) {
            project.setRaisedMoney(project.getRaisedMoney().add(transaction.getMoney()));
            project.setDonorCount(project.getDonorCount()+1);
            projectRepository.save(project);
        }
    }
}

