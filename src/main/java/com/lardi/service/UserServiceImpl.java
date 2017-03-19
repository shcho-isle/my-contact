package com.lardi.service;

import com.lardi.AuthorizedUser;
import com.lardi.model.User;
import com.lardi.model.Role;
import com.lardi.repository.RoleRepository;
import com.lardi.repository.UserRepository;
import com.lardi.util.PasswordUtil;
import com.lardi.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static com.lardi.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        User savedUser = repository.save(user);
        Role role = new Role(savedUser.getId());
        roleRepository.save(role);
        return user;
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
        return new AuthorizedUser(u);
    }
}
