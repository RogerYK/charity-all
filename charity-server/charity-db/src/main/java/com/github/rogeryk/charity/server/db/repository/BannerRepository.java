package com.github.rogeryk.charity.server.db.repository;

import com.github.rogeryk.charity.server.db.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
}
