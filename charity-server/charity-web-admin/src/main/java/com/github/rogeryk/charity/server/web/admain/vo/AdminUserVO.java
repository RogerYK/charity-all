package com.github.rogeryk.charity.server.web.admain.vo;

import com.github.rogeryk.charity.server.db.domain.AdminUser;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class AdminUserVO {
    private String username;
    private List<String> authorities;

    public static AdminUserVO from(AdminUser adminUser) {
        if (adminUser == null) {
            return null;
        }
        AdminUserVO adminUserVO = new AdminUserVO();
        adminUserVO.setUsername(adminUser.getUsername());
        List<String> authorities = adminUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        adminUserVO.setAuthorities(authorities);
        return adminUserVO;
    }
}
