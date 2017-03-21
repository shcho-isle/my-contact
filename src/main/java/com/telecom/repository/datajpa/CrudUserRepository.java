package com.telecom.repository.datajpa;

import com.telecom.model.User;
import org.springframework.data.repository.CrudRepository;

public interface CrudUserRepository extends CrudRepository<User, Integer> {

    User getByLogin(String login);
}