package com.lardi.repository.datajpa;

import com.lardi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface CrudUserRepository extends CrudRepository<User, Integer> {

    User getByLogin(String login);
}