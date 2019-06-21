package com.github.rogeryk.charity.repository;

import com.github.rogeryk.charity.domain.Category;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.domain.vo.ProjectVO;
import com.github.rogeryk.charity.utils.PageData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findByCategory(Category category, Pageable pageable);

    int countProjectByAuthor(User user);

    Page<Project> findAllByNameLike(String name, Pageable pageable);

    Page<Project> findAllByFollowedUsersContaining(User user, Pageable pageable);

    Page<Project> findAllByAuthor(User user, Pageable pageable);

    Optional<ProjectVO> findProjectById(Long id);

    boolean existsByIdEqualsAndFollowedUsersContaining(Long id, User user);

//    @Query(value = "select  id, `name`, img, gallery, content, summary, raised_money, target_money, start_time, end_time, bumo_address, donor_count, author_id, category_id from project where match(`name`, summary, content) against(:keyword) limit :page,:size", nativeQuery = true)
    @Query(value = "select * from project where match(`name`, summary, content) against(:keyword) limit :page,:size",
            nativeQuery = true)
    List<Project> selectProject(@Param("keyword") String keyword, @Param("page") int page, @Param("size") int size);

    @Query(value = "select count(*) from project where match(`name`, summary, content) against(:keyword)",
            nativeQuery = true)
    long searchProjectCount(@Param("keyword") String keyword);

//    @Query(value = "select FOUND_ROWS() as `total`", nativeQuery = true)
//    List<BigInteger> searchFoundRows();


    default PageData<Project> searchProject(String text, int page, int size) {
        PageData<Project> pageData = new PageData<>();
        pageData.setContent(selectProject(text, page, size));
        pageData.setTotal(searchProjectCount(text));
        return pageData;
    }

}
