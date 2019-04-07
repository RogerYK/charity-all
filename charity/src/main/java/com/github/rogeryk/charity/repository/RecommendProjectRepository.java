package com.github.rogeryk.charity.repository;

import com.github.rogeryk.charity.domain.RecommendProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendProjectRepository extends JpaRepository<RecommendProject, Long> {

    @Query(value = "select * from recommend_project limit :n", nativeQuery = true)
    List<RecommendProject> findTop(@Param("n") int n);
}
