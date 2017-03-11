package com.lardi.service.jsonService;

import com.lardi.model.User;
import com.lardi.model.Role;

import java.io.IOException;

public interface RoleService {
    void createRole(User user) throws IOException;

    Role getRoleByUserId(Integer userId) throws Exception;
}
