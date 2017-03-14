package com.lardi.repository.datajpa;

import com.lardi.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface CrudRoleRepository extends CrudRepository<Role, Integer> {
}