package com.github.rogeryk.charity.server.db.repository;

import com.github.rogeryk.charity.server.db.domain.CountAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CountAnalysisRepository extends JpaRepository<CountAnalysis, Long> {

    @Query(value = "select sum(count) from count_analysis where count_type = :countType", nativeQuery = true)
    int sumAllCount(@Param("countType") int countType);

    @Query(value = "select * from count_analysis where `date` >= :startTime and `date` < :endTime and count_type = :countType", nativeQuery = true)
    List<CountAnalysis> findByRange(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("countType") int countType);

    Optional<CountAnalysis> findByDateEqualsAndCountTypeEquals(Date date, CountAnalysis.CountType countType);
}
