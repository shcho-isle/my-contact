package com.lardi.repository.datajpa;

import com.lardi.model.User;
import com.lardi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Profile("mysql")
@Repository("dataJpaUserRepository")
public class DataJpaUserRepositoryImpl implements UserRepository {
    private static final Sort SORT_NAME_EMAIL = new Sort("name", "email");

    @Autowired
    private CrudUserRepository crudRepository;

    @Override
    public User save(User user) {
        return crudRepository.save(user);
    }

    @Override
    public User get(Integer id) {
        return crudRepository.findOne(id);
    }

    @Override
    public User getByLogin(String login) {
        return crudRepository.getByLogin(login);
    }
}
