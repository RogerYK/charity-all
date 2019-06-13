package com.github.rogeryk.charity.service;

import com.github.rogeryk.charity.bumo.BumoService;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.Transaction;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.exception.ServiceException;
import com.github.rogeryk.charity.repository.ProjectRepository;
import com.github.rogeryk.charity.repository.TransactionRepository;
import com.github.rogeryk.charity.repository.UserRepository;
import com.github.rogeryk.charity.utils.ErrorCodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BumoService bumoService;


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void donate(Long userId, Long projectId, BigDecimal amount) {
        Project project = projectRepository.findById(projectId).orElseThrow(RuntimeException::new);
        User user = userRepository.
                findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));

        if (user.getMoney().compareTo(amount) < 0) {
            throw new ServiceException(ErrorCodes.USER_NO_MONEY, "用户余额不足");
        }
        user.setMoney(user.getMoney().subtract(amount));
        userRepository.save(user);

        project.setRaisedMoney(project.getRaisedMoney().add(amount));
        project.setDonorCount(project.getDonorCount()+1);
        projectRepository.save(project);

        Transaction ts = new Transaction();

        ts.setPayer(user);
        ts.setMoney(amount);
        ts.setProject(project);
        ts.setType(Transaction.TransactionType.Donation);

        transactionRepository.save(ts);

        String hash = bumoService.assetSend(user.getBumoAddress(), project.getBumoAddress(), user.getBumoPrivateKey(), amount.longValue());

        ts.setHash(hash);
        transactionRepository.save(ts);
    }

    @Transactional
    public void recharge(Long userId, Long amount) {
        // 刷新用户数据
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));
        user.setMoney(user.getMoney().add(BigDecimal.valueOf(amount)));
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setPayee(user);
        transaction.setType(Transaction.TransactionType.Recharge);
        transaction.setMoney(BigDecimal.valueOf(amount));
        transactionRepository.save(transaction);

        String hash = bumoService.recharge(user.getBumoAddress(), amount);

        transaction.setHash(hash);
        transactionRepository.save(transaction);
    }

    public Page<Transaction> donationBy(Long userId, Pageable pageable) {
        return transactionRepository.findAllByPayer_IdAndType(userId,
                Transaction.TransactionType.Donation,
                pageable);
    }

}
