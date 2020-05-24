package com.github.rogeryk.charity.server.db.repository;

import com.github.rogeryk.charity.server.db.domain.Category;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.domain.vo.ProjectVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    Page<Project> findAllByStatusIn(List<Integer> status, Pageable pageable);

    List<Project> findAllByStatusIn(List<Integer> status);

    @Query(value = "select * from project where status = 0 and id > :lastId order by id asc limit :size", nativeQuery = true)
    List<Project> findCreatingProject(@Param("lastId") long lastId, @Param("size") int size);

    Page<Project> findByCategoryAndStatusIn(Category category, List<Integer> statusList, Pageable pageable);

    @Query(value = "select * from project where status = 2 and now() > end_time and id > :id order by id asc limit :len", nativeQuery = true)
    List<Project> findOverdueProjects(@Param("id") Long id, @Param("len") int len);

    int countProjectByAuthor(User user);

    Page<Project> findAllByNameLike(String name, Pageable pageable);

    Page<Project> findAllByFollowedUsersContaining(User user, Pageable pageable);

    Page<Project> findAllByAuthor(User user, Pageable pageable);

    Optional<ProjectVO> findProjectById(Long id);

    boolean existsByIdEqualsAndFollowedUsersContaining(Long id, User user);

    @Modifying
    @Query(value = "update project set deleted_time = now() where id in (:ids)", nativeQuery = true)
    int deleteAll(@Param("ids") List<Long> ids);

    @Modifying
    @Query(value = "update project set watch_count=watch_count+1  where id=:id", nativeQuery = true)
    int incrementNewsWatchCount(@Param("id") Long id);

//    @Query(value = "select  id, `name`, img, gallery, content, summary, raised_money, target_money, start_time, end_time, bumo_address, donor_count, author_id, category_id from project where match(`name`, summary, content) against(:keyword) limit :page,:size", nativeQuery = true)
    @Query(value = "select * from project where match(`name`, summary, content) against(:keyword) limit :page,:size",
            nativeQuery = true)
    List<Project> selectProject(@Param("keyword") String keyword, @Param("page") int page, @Param("size") int size);

    @Query(value = "select count(*) from project where match(`name`, summary, content) against(:keyword)",
            nativeQuery = true)
    long searchProjectCount(@Param("keyword") String keyword);

//    @Query(value = "select FOUND_ROWS() as `total`", nativeQuery = true)
//    List<BigInteger> searchFoundRows();

    @Query(value = "select date_format(created_time, '%Y-%m-%d') as `date`, count(*) as `count` from project where created_time >= :startTime and created_time < :endTime group by date_format(created_time, '%Y-%m-%d')", nativeQuery = true)
    List<Map<String, Object>> scanCountData(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    default PageData<Project> searchProject(String text, int page, int size) {
        PageData<Project> pageData = new PageData<>();
        pageData.setContent(selectProject(text, page, size));
        pageData.setTotal(searchProjectCount(text));
        return pageData;
    }

}
