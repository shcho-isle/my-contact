package com.lardi.service;

import com.lardi.model.User;
import com.lardi.util.exception.NotFoundException;

import java.io.IOException;

public interface UserService {

    User save(User user) throws IOException;

    User get(Integer id) throws NotFoundException, IOException;

    User getByLogin(String login) throws NotFoundException, IOException;
}
