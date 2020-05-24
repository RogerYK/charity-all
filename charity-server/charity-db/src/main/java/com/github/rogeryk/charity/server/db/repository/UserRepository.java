package com.github.rogeryk.charity.server.db.repository;

import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.CountData;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByPhoneNumber(String phoneNumber);

    @Query(value = "select * from `user` where match(`nick_name`) against (:keyword) limit :page, :size", nativeQuery = true)
    List<User> searchUserData(@Param("keyword") String keyword, @Param("page") int page, @Param("size") int size);

    @Query(value = "select count(*) from `user` where match(`nick_name`) against (:keyword)", nativeQuery = true)
    long searchUserTotal(@Param("keyword") String keyword);

    @Query(value = "select date_format(created_time, '%Y-%m-%d') as `date`, count(*) as `count` from user where created_time >= :startTime and created_time < :endTime group by date_format(created_time, '%Y-%m-%d')", nativeQuery = true)
    List<CountData> scanCountData(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Query(value = "select * from `user` where user_status = 0 and id > :lastId order by id asc limit :size", nativeQuery = true)
    List<User> findCreatingUser(@Param("lastId") long lastId, @Param("size") int size);

    int countUserByFollowedUsersContaining(User user);

    default PageData<User> searchUser(String text, int page, int size) {
        PageData<User> pageData = new PageData<>();
        pageData.setContent(searchUserData(text, page, size));
        pageData.setTotal(searchUserTotal(text));
        return pageData;
    }

}
