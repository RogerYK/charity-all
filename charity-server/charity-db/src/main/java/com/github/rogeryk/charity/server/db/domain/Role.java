package com.github.rogeryk.charity.server.db.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Data
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Operation> authorities;

    @Override
    public String getAuthority() {
        return name;
    }
}
