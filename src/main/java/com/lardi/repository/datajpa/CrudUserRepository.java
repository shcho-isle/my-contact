package com.lardi.repository.datajpa;

import com.lardi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface CrudUserRepository extends CrudRepository<User, Integer> {

    User getByLogin(String login);
}