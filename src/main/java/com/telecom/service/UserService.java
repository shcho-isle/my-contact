package com.telecom.service;

import com.telecom.model.User;
import com.telecom.service.exception.NotFoundException;

public interface UserService {

    User save(User user);

    User get(Integer id) throws NotFoundException;
}
