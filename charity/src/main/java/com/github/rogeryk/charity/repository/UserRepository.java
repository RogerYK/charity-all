package com.github.rogeryk.charity.repository;

import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.domain.form.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhoneNumberAndPassword(String phoneNumber, String password);

    User findByPhoneNumber(String phoneNumber);


}
