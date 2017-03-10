package com.lardi.repository.datajpa;

import com.lardi.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudUserRolesRepository extends CrudRepository<UserRole, Integer> {
}