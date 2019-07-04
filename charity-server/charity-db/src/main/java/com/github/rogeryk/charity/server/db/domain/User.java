package com.github.rogeryk.charity.server.db.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 11)
    private String phoneNumber;

    @JsonIgnore
    private String password;

    private String nickName = "75500";

    private String avatar;

    private String presentation;

    private String profession;

    private String address;

    private Integer sex;

    private Date birthday;

    private BigDecimal money = new BigDecimal(0);

    @JsonIgnore
    @Version
    private Long version;

    @JsonIgnore
    private String bumoAddress;

    @JsonIgnore
    private String bumoPrivateKey;


    @JsonIgnore
    @ManyToMany()
    private List<Project> followProjects;

    @JsonIgnore
    @ManyToMany
    private List<User> followedUsers;

    @JsonIgnore
    @OneToMany(mappedBy = "payer")
    private List<Transaction> payRecord;

    @JsonIgnore
    @OneToMany(mappedBy = "payee")
    private List<Transaction> receiveRecord;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Project> releasedProjects;

    @JsonIgnore
    @OneToMany(mappedBy = "commenter")
    private List<Comment> comments;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
