package com.github.rogeryk.charity.repository;

import com.github.rogeryk.charity.domain.News;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = "select * from news order by watch_count desc limit :n", nativeQuery = true)
    List<News> findHotNews(@Param("n") int n);

    @Query(value = "select * from news order by created_time desc limit :n", nativeQuery = true)
    List<News> findLatestNews(@Param("n") int n);

    Page<News> findByAuthor_Id(Long id, Pageable pageable);

}
