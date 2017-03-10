package com.lardi.service.jsonService;

import com.lardi.model.User;
import com.lardi.model.UserRole;

import java.io.IOException;

public interface UserRoleService {
    void createRole(User user) throws IOException;

    UserRole getRoleByUserId(Integer userId) throws Exception;
}
