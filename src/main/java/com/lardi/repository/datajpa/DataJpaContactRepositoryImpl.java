package com.lardi.repository.datajpa;

import com.lardi.model.Contact;
import com.lardi.model.User;
import com.lardi.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        return crudRepository.getAll(user.getId());
    }

    @Override
    @Transactional
    public Contact save(Contact contact, Integer userId) {
        contact.setUserId(userId);
        return crudRepository.save(contact);
    }

    @Override
    @Transactional
    public Contact update(Contact contact, Integer userId) {
        if (get(contact.getId(), userId) == null) {
            return null;
        }
        User user = crudUserRepository.findOne(userId);
        contact.setUserId(user.getId());
        return crudRepository.save(contact);
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        User user = crudUserRepository.findOne(userId);
        return crudRepository.delete(id, user.getId()) != 0;
    }

    @Override
    public List<Contact> getFiltered(String filterRequest, Integer userId) {
        User user = crudUserRepository.findOne(userId);
        return crudRepository.getFiltered("%" + filterRequest + "%", user.getId());
    }

    @Override
    public Contact get(Integer id, Integer userId) {
        Contact contact = crudRepository.findOne(id);
        return contact != null && Objects.equals(contact.getUserId(), userId) ? contact : null;
    }
}
