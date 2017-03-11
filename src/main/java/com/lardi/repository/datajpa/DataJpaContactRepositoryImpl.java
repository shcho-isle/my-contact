package com.lardi.repository.datajpa;

import com.lardi.model.Contact;
import com.lardi.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dataJpaContactRepository")
public class DataJpaContactRepositoryImpl implements ContactRepository {

    @Autowired
    private CrudContactRepository crudRepository;

    @Override
    public List<Contact> getByUserLogin(String login) {
        return crudRepository.findByUserLogin(login);
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
    public boolean delete(Integer id) {
        return crudRepository.delete(id.intValue()) != 0;
    }
}
