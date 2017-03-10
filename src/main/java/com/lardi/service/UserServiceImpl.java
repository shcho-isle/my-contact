package com.lardi.service;

import com.lardi.model.User;
import com.lardi.model.UserRole;
import com.lardi.repository.datajpa.CrudUserRepository;
import com.lardi.repository.datajpa.CrudUserRolesRepository;
import com.lardi.util.PasswordUtil;
import com.lardi.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("userService")
@ComponentScan(basePackages = "com.lardi")
public class UserServiceImpl implements UserService {

    @Autowired
    private CrudUserRepository repository;

    @Autowired
    private CrudUserRolesRepository roleRepository;

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        UserRole role = new UserRole();
        User savedUser = repository.save(user);
        role.setUserId(savedUser.getId());
        role.setRole();
        roleRepository.save(role);
        return repository.save(user);
    }

    @Override
    public User get(Integer id) throws NotFoundException {
        User user = repository.findOne(id);
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
