package com.lardi.model;

import javax.persistence.*;

@Entity
@Table(name = "user_roles", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_idx")})
public class UserRole extends BaseEntity {
    @Column(name = "role")
    private String role;

    @Column(name = "user_id")
    private Integer userId;

    public UserRole() {
    }

    public UserRole(Integer userId) {
        this(null, userId);
    }

    public UserRole(Integer id, Integer userId) {
        super(id);
        this.role = "ROLE_ADMIN";
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole() {
        this.role = "ROLE_ADMIN";
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + getId() +
                ", role='" + role + '\'' +
                '}';
    }
}
