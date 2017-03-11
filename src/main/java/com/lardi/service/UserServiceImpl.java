package com.lardi.service;

import com.lardi.model.User;
import com.lardi.model.Role;
import com.lardi.repository.RoleRepository;
import com.lardi.repository.UserRepository;
import com.lardi.util.PasswordUtil;
import com.lardi.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("userService")
@ComponentScan(basePackages = "com.lardi")
public class UserServiceImpl implements UserService {

    @Qualifier(value = "dataJpaUserRepository")
    @Autowired
    private UserRepository repository;

    @Qualifier(value = "dataJpaRoleRepository")
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

        return repository.save(user);
    }

    @Override
    public User get(Integer id) throws NotFoundException {
        User user = repository.get(id);
        if (user == null) {
            throw new NotFoundException("Not found entity with id=" + id);
        }
        return user;
    }

    @Override
    public User getByLogin(String login) throws NotFoundException {
        Assert.notNull(login, "email must not be null");
        User user = repository.getByLogin(login);
        if (user == null) {
            throw new NotFoundException("Not found entity with login=" + login);
        }
        return user;
    }
}
