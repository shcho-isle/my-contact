package com.telecom.repository.datajpa;

import com.telecom.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CrudRoleRepository extends CrudRepository<Role, Integer> {

    @Query("SELECT r FROM Role r WHERE r.userId=:userId")
    Set<Role> getAll(@Param("userId") Integer userId);
}