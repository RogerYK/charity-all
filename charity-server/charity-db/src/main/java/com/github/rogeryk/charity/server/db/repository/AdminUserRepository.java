package com.github.rogeryk.charity.server.db.repository;

import com.github.rogeryk.charity.server.db.domain.AdminUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    AdminUser findByUsername(String username);
}
