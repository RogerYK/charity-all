package com.github.rogeryk.charity.repository;

import com.github.rogeryk.charity.domain.News;
import com.github.rogeryk.charity.utils.PageData;

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

    @Query(value = "select * from news where match (`name`, content, introduce, title) against (:keyword) limit :page,:size", nativeQuery = true)
    List<News> searchNewsData(@Param("keyword") String keyword, @Param("page") int page, @Param("size") int size);

    @Query(value = "select count(*) from news where match (`name`, content, introduce, title) against (:keyword)", nativeQuery = true)
    long searchNewsCount(@Param("keyword") String keyword);

    default PageData<News> searchNews(String keyword, int page, int size) {
        PageData<News> pageData = new PageData<>();
        pageData.setContent(searchNewsData(keyword, page, size));
        pageData.setTotal(searchNewsCount(keyword));
        return pageData;
    }

}
