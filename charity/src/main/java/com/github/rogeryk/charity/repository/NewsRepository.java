package com.github.rogeryk.charity.repository;

import com.github.rogeryk.charity.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {


}
