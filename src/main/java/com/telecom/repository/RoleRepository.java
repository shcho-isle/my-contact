package com.telecom.repository;

import com.telecom.model.Role;

import java.util.Set;

public interface RoleRepository {
    void save(Role role);

    Set<Role> getAll(Integer userId);
}
