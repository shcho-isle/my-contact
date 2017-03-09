package com.lardi.repository;

import com.lardi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Override
    User save(User user);

    User findByLogin(String login);

    @Query("select u from User u where u.fullName=:fullName")
    User finByFullName(@Param("fullName") String fullName);

}