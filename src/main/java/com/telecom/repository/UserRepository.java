package com.telecom.repository;

import com.telecom.model.User;

public interface UserRepository {
    User save(User user);

    // null if not found
    User get(Integer id);

    // null if not found
    User getByLogin(String login);
}
