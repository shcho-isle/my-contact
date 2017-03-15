package com.lardi.repository.datajpa;

import com.lardi.model.Contact;
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

    @Override
    public List<Contact> getAll(Integer userId) {
        return crudRepository.getAll(userId);
    }

    @Override
    @Transactional
    public Contact save(Contact contact, Integer userId) {
        if (!contact.isNew() && get(contact.getId(), userId) == null) {
            return null;
        }
        contact.setUserId(userId);
        return crudRepository.save(contact);
    }

    @Override
    @Transactional
    public Contact update(Contact contact, Integer userId) {
        return save(contact, userId);
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public List<Contact> getFiltered(String filterRequest, Integer userId) {
        return crudRepository.getFiltered("%" + filterRequest + "%", userId);
    }

    @Override
    public Contact get(Integer id, Integer userId) {
        Contact contact = crudRepository.findOne(id);
        return contact != null && Objects.equals(contact.getUserId(), userId) ? contact : null;
    }
}