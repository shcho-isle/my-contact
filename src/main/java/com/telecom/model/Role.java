package com.telecom.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "user_roles", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_idx")})
public class Role extends BaseEntity implements GrantedAuthority {
    @Column(name = "role")
    private String name;

    @Column(name = "user_id")
    private Integer userId;

    public Role() {
    }

    public Role(Integer userId) {
        this(null, userId);
    }

    public Role(Integer id, Integer userId) {
        super(id);
        this.name = "ROLE_ADMIN";
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = "ROLE_ADMIN";
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + getId() +
                ", role='" + name + '\'' +
                '}';
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
