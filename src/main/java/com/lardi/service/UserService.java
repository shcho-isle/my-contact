package com.lardi.service;

import com.lardi.model.User;

public interface UserService {
    User createUser(User user) throws Exception;

    User getUser(String name) throws Exception;

    boolean checkRegisteredUsers(String login, String fullName) throws Exception;
}
