package com.lardi.repository;

import java.util.List;

import com.lardi.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesRepository extends CrudRepository<UserRole, Integer> {

	@Query("select r.role from UserRole r where r.userId=:userId")
    List<String> findRoleUserId(@Param("userId") Integer userId);

    @Override
    UserRole save(UserRole userRole);
}