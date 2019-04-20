package com.github.rogeryk.charity.service;

import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.Transaction;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.repository.ProjectRepository;
import com.github.rogeryk.charity.repository.TransactionRepository;
import com.github.rogeryk.charity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void donate(User user, Long projectId, BigDecimal amount) {
        Project project = projectRepository.findById(projectId).orElseThrow(RuntimeException::new);
        User payee = project.getAuthor();

        Transaction ts = new Transaction();

        ts.setPayer(user);
        ts.setPayee(payee);
        ts.setMoney(amount);
        ts.setProject(project);
        ts.setTradeNo(UUID.randomUUID().toString().replace("-", ""));
        ts.setType(Transaction.TransactionType.Donation);

        transactionRepository.save(ts);
        payee.setMoney(payee.getMoney().add(amount));
        userRepository.save(payee);
    }

    public Page<Transaction> donationBy(User user, Pageable pageable) {
        return transactionRepository.findAllByPayerIsAndType(user,
                Transaction.TransactionType.Donation,
                pageable);
    }

}
