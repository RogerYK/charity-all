package com.github.rogeryk.charity.server.db.repository;

import com.github.rogeryk.charity.server.db.domain.Identification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentificationRepository extends JpaRepository<Identification, Long>, JpaSpecificationExecutor<Identification> {
    Optional<Identification> findByUserId(Long userID);
}
