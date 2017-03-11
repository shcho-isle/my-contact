package com.lardi.repository;

import com.lardi.model.User;

public interface UserRepository {
    User save(User user);

    // null if not found
    User get(Integer id);

    // null if not found
    User getByLogin(String login);
}
