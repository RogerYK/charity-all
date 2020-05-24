package com.github.rogeryk.charity.server.web.service;

import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.mq_data.OrderEvent;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;
import com.github.rogeryk.charity.server.core.util.IDWorker;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.Transaction;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;
import com.github.rogeryk.charity.server.db.repository.TransactionRepository;
import com.github.rogeryk.charity.server.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private IDWorker idWorker;

    @Transactional
    public void donate(Long userId, Long projectId, BigDecimal amount) {
        Long uniqueId = idWorker.nextId();

        Project project = projectRepository.findById(projectId).orElseThrow(RuntimeException::new);
        User user = userRepository.
                findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));

        if (user.getMoney().compareTo(amount) < 0) {
            throw new ServiceException(ErrorCodes.USER_NO_MONEY, "用户余额不足");
        }

        Transaction transaction = new Transaction();
        transaction.setPayer(user);
        transaction.setMoney(amount);
        transaction.setProject(project);
        transaction.setType(Transaction.TransactionType.Donation);
        transaction.setUniqueId(uniqueId);
        transaction.setStatus(Transaction.TransactionStatus.Paying);
        transactionRepository.saveAndFlush(transaction);

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderType(OrderEvent.DONATION);
        orderEvent.setUserId(userId);
        orderEvent.setAnotherId(projectId);
        orderEvent.setAmount(amount);
        orderEvent.setUniqueId(uniqueId);
        rocketMQTemplate.syncSend("order-event", orderEvent);
    }

    public void recharge(Long userId, BigDecimal amount) {
        Long uniqueId = idWorker.nextId();

        User user = userRepository.
                findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));

//        if (user.getMoney().compareTo(amount) < 0) {
//            throw new ServiceException(ErrorCodes.USER_NO_MONEY, "用户余额不足");
//        }

        Transaction transaction = new Transaction();
        transaction.setPayee(user);
        transaction.setMoney(amount);
        transaction.setType(Transaction.TransactionType.Recharge);
        transaction.setUniqueId(uniqueId);
        transaction.setStatus(Transaction.TransactionStatus.Paying);
        transactionRepository.saveAndFlush(transaction);

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderType(OrderEvent.RECHARGE);
        orderEvent.setUserId(userId);
        orderEvent.setAmount(amount);
        orderEvent.setUniqueId(uniqueId);
        log.info("send recharge event {}", orderEvent);
        rocketMQTemplate.syncSend("order-event", orderEvent);
    }

    public Page<Transaction> donationBy(Long userId, Pageable pageable) {
        return transactionRepository.findAllByPayer_IdAndType(userId,
                Transaction.TransactionType.Donation,
                pageable);
    }

}
