package com.telecom.repository.datajpa;

import com.telecom.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface CrudRoleRepository extends CrudRepository<Role, Integer> {
}