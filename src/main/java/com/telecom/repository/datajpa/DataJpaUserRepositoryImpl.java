package com.telecom.repository.datajpa;

import com.telecom.model.User;
import com.telecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("mysql")
@Repository
public class DataJpaUserRepositoryImpl implements UserRepository {
    private final CrudUserRepository crudRepository;

    @Autowired
    public DataJpaUserRepositoryImpl(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

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
