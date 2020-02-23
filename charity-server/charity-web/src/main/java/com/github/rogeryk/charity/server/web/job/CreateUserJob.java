package com.github.rogeryk.charity.server.web.job;

import com.github.rogeryk.charity.server.core.bumo.BumoService;
import com.github.rogeryk.charity.server.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateUserJob {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BumoService bumoService;


    public void checkCreatingUser() {
        long lastId = -1;

    }
}
