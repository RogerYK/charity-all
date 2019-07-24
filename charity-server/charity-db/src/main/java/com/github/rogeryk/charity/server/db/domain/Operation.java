package com.github.rogeryk.charity.server.db.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Operation implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
