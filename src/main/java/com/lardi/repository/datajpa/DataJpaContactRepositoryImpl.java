package com.lardi.repository.datajpa;

import com.lardi.model.Contact;
import com.lardi.model.User;
import com.lardi.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Profile("mysql")
@Repository("dataJpaContactRepository")
public class DataJpaContactRepositoryImpl implements ContactRepository {

    @Autowired
    private CrudContactRepository crudRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public List<Contact> getAll(Integer userId) {
        User user = crudUserRepository.findOne(userId);
        return crudRepository.findByUserLogin(user.getLogin());
    }

    @Override
    public Contact save(Contact contact) {
        return crudRepository.save(contact);
    }

    @Override
    public boolean update(Contact contact) {
        return crudRepository.save(contact).getId().equals(contact.getId());
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        User user = crudUserRepository.findOne(userId);
        return crudRepository.delete(id, user.getLogin()) != 0;
    }

    @Override
    public List<Contact> getFiltered(String filterRequest, Integer userId) {
        User user = crudUserRepository.findOne(userId);
        return crudRepository.getFiltered("%" + filterRequest + "%", user.getLogin());
    }

    @Override
    public Contact get(Integer id, Integer userId) {
        Contact contact = crudRepository.findOne(id);
        User user = crudUserRepository.findOne(userId);
        return contact != null && Objects.equals(contact.getUserLogin(), user.getLogin()) ? contact : null;
    }
}
