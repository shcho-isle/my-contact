package com.telecom.service;

import com.telecom.AuthorizedUser;
import com.telecom.model.User;
import com.telecom.model.Role;
import com.telecom.repository.UserRepository;
import com.telecom.util.PasswordUtil;
import com.telecom.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;

import static com.telecom.service.ValidationUtil.checkNotFoundWithId;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setLogin(user.getLogin().toLowerCase());
        return repository.save(user);
    }

    @Override
    public User get(Integer id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = repository.getByLogin(login.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException("User " + login + " is not found");
        }
        return new AuthorizedUser(u, Collections.singletonList(Role.ROLE_ADMIN));
    }
}
