package com.github.rogeryk.charity.server.web.admain.service;

import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;
import com.github.rogeryk.charity.server.db.domain.AdminUser;
import com.github.rogeryk.charity.server.db.repository.AdminUserRepository;
import com.github.rogeryk.charity.server.web.admain.vo.AdminUserVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service("adminUserServiceVo")
@Slf4j
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    public AdminUserVO currentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        log.info("current:username"+username);
        AdminUser adminUser = adminUserRepository.findByUsername(username);
        if (adminUser == null) {
            throw ServiceException.of(ErrorCodes.NEWS_NOT_EXIST, "用户不存在");
        }
        return AdminUserVO.from(adminUser);
    }

}
