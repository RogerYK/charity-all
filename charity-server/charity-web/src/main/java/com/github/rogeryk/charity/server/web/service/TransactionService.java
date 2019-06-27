package com.github.rogeryk.charity.server.web.service;

import com.github.rogeryk.charity.server.core.mq_data.OrderEvent;
import com.github.rogeryk.charity.server.core.util.IDWorker;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.Transaction;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;
import com.github.rogeryk.charity.server.db.repository.TransactionRepository;
import com.github.rogeryk.charity.server.db.repository.UserRepository;
import com.github.rogeryk.charity.server.core.bumo.BumoService;
import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private IDWorker idWorker;

    public void donate(Long userId, Long projectId, BigDecimal amount) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderType(OrderEvent.DONATION);
        orderEvent.setUserId(userId);
        orderEvent.setAnotherId(projectId);
        orderEvent.setAmount(amount);
        orderEvent.setUniqueId(idWorker.nextId());
        rocketMQTemplate.syncSend("order-event", orderEvent);
    }

    public void recharge(Long userId, BigDecimal amount) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderType(OrderEvent.RECHARGE);
        orderEvent.setUserId(userId);
        orderEvent.setAmount(amount);
        orderEvent.setUniqueId(idWorker.nextId());
        rocketMQTemplate.syncSend("order-event", orderEvent);
    }

    public Page<Transaction> donationBy(Long userId, Pageable pageable) {
        return transactionRepository.findAllByPayer_IdAndType(userId,
                Transaction.TransactionType.Donation,
                pageable);
    }

}
