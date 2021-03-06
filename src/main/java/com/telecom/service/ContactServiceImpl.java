package com.telecom.service;

import com.telecom.model.Contact;
import com.telecom.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

import static com.telecom.service.ValidationUtil.checkNotFoundWithId;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository repository;

    @Autowired
    public ContactServiceImpl(ContactRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Contact> getAll(Integer userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<Contact> getFiltered(String filterRequest, Integer userId) {
        Assert.notNull(filterRequest, "filter request must not be null");
        return repository.getFiltered(filterRequest, userId);
    }

    @Override
    public Contact get(Integer id, Integer userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public Contact save(Contact contact, Integer userId) {
        Assert.notNull(contact, "contact must not be null");
        return repository.save(contact, userId);
    }

    @Override
    public Contact update(Contact contact, Integer userId) {
        Assert.notNull(contact, "contact must not be null");
        return checkNotFoundWithId(repository.save(contact, userId), contact.getId());
    }

    @Override
    public void delete(int id, Integer userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }
}
