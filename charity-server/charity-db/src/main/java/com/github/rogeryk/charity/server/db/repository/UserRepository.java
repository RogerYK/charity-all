package com.github.rogeryk.charity.server.db.repository;

import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhoneNumberAndPassword(String phoneNumber, String password);

    User findByPhoneNumber(String phoneNumber);

    @Query(value = "select * from `user` where match(`nick_name`) against (:keyword) limit :page, :size", nativeQuery = true)
    List<User> searchUserData(@Param("keyword") String keyword, @Param("page") int page, @Param("size") int size);

    @Query(value = "select * from `user` where match(`nick_name`) against (:keyword)", nativeQuery = true)
    long searchUserTotal(@Param("keyword") String keyword);

    default PageData<User> searchUser(String text, int page, int size) {
        PageData<User> pageData = new PageData<>();
        pageData.setContent(searchUserData(text, page, size));
        pageData.setTotal(searchUserTotal(text));
        return pageData;
    }

}
