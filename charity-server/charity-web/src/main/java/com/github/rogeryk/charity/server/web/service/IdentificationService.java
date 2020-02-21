package com.github.rogeryk.charity.server.web.service;


import com.github.rogeryk.charity.server.core.exception.ServiceExceptions;
import com.github.rogeryk.charity.server.db.domain.Identification;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.repository.IdentificationRepository;
import com.github.rogeryk.charity.server.db.repository.UserRepository;
import com.github.rogeryk.charity.server.web.controller.form.IdentificationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdentificationService {
    @Autowired
    private IdentificationRepository identificationRepository;
    @Autowired
    private UserRepository userRepository;

    public void saveIdentification(Long userId ,IdentificationForm form) {
        User user = userRepository.findById(userId).orElseThrow(() -> ServiceExceptions.USER_NOT_EXIST);
        user.setIdentifyStatus(User.IdentifyStatus.Identifying);
        userRepository.save(user);

        Optional<Identification> optional = identificationRepository.findByUserId(userId);
        Identification identification = optional.orElseGet(Identification::new);
        identification.setUserId(userId);
        form.merge(identification);
        identification.setIdentificationState(Identification.IdentificationState.Apply);
        identificationRepository.save(identification);
    }
}
