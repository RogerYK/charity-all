package com.github.rogeryk.charity.server.web.job;

import com.github.rogeryk.charity.server.core.bumo.BumoService;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.repository.UserRepository;
import io.bumo.model.response.result.TransactionGetInfoResult;
import io.bumo.model.response.result.data.TransactionHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CreateUserJob {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BumoService bumoService;

    @Scheduled(fixedRate = 5 * 60 * 1000)   //每五分钟执行一次
    public void checkCreatingUser() {
        log.info("begin check creating user");
        long lastId = -1;
        int size = 50;
        while (true) {
            List<User> userList = userRepository.findCreatingUser(lastId, size);
            for (User user : userList) {
                try {
                    lastId = user.getId();
                    String hash = user.getActiveHash();
                    TransactionGetInfoResult result = bumoService.getTransaction(hash);
                    if (result.getTotalCount() == 0 || result.getTransactions().length == 0) {
                        log.error("not find transaction in block chain");
                        throw new RuntimeException("not find transaction in block chain");
                    }
                    TransactionHistory[] transactionHistories = result.getTransactions();
                    TransactionHistory transactionHistory = null;
                    for (TransactionHistory history : transactionHistories) {
                        if (history.getHash().equals(hash)) {
                            transactionHistory = history;
                            break;
                        }
                    }
                    if (transactionHistory == null) {
                        log.error("not find transaction user {}", user.getId());
                        continue;
                    }
                    if (transactionHistory.getErrorCode() != 0) {
                        log.error("active user (id:{}) transaction error (code:{},desc{})",
                                user.getId(),
                                transactionHistory.getErrorCode(),
                                transactionHistory.getErrorDesc());
                        continue;
                    }
                    user.setUserStatus(User.UserStatus.Created);
                    userRepository.saveAndFlush(user);

                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("check creating project fail {}", e.toString());
                }
            }
            if (userList.size() < size) {
                break;
            }
        }
    }
}
