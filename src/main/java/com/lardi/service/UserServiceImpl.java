package com.lardi.service;

import com.lardi.model.User;
import com.lardi.repository.UserRepository;
import com.lardi.model.UserRole;
import com.lardi.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
@ComponentScan(basePackages = "com.lardi")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Override
    public User createUser(User user) {
        if (!checkRegisteredUsers(user.getLogin(), user.getFullName()))
            return null;
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        UserRole role = new UserRole();
        User savedUser = userRepository.save(user);
        role.setUserId(savedUser.getId());
        role.setRole();
        userRolesRepository.save(role);
        return savedUser;
    }

    @Override
    public User getUser(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public boolean checkRegisteredUsers(String login, String fullName) {
        return userRepository.findByLogin(login) == null & userRepository.finByFullName(fullName) == null;
    }
}
