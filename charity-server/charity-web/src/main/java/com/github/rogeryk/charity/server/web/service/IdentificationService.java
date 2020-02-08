package com.github.rogeryk.charity.server.web.service;


import com.github.rogeryk.charity.server.db.domain.Identification;
import com.github.rogeryk.charity.server.db.repository.IdentificationRepository;
import com.github.rogeryk.charity.server.web.controller.form.IdentificationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdentificationService {
    @Autowired
    private IdentificationRepository identificationRepository;

    public void saveIdentification(Long userId ,IdentificationForm form) {
        Optional<Identification> optional = identificationRepository.findByUserId(userId);
        Identification identification = optional.orElseGet(Identification::new);
        identification.setUserId(userId);
        form.merge(identification);
        identification.setIdentificationState(Identification.IdentificationState.Apply);
        identificationRepository.save(identification);
    }
}
