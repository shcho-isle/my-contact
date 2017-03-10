package com.lardi.repository.datajpa;

import com.lardi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudUserRepository extends CrudRepository<User, Integer> {

    @Query("select u from User u where u.fullName=:fullName")
    User finByFullName(@Param("fullName") String fullName);

    User getByLogin(String login);
}