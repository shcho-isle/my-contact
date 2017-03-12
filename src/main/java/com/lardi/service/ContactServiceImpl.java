package com.lardi.service;

import com.lardi.model.Contact;
import com.lardi.repository.ContactRepository;
import com.lardi.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import static com.lardi.util.ValidationUtil.checkNotFoundWithId;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository repository;

    @Override
    public List<Contact> getAll(Integer userId) {
        List<Contact> list = new ArrayList<>();
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        repository.getByUserLogin(login).forEach(list::add);
        return list;
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
    public Integer save(Contact contact) {
        return repository.save(contact).getId();
    }

    @Override
    public boolean update(Contact contact) {
        return repository.update(contact);
    }

    @Override
    public boolean delete(Integer id) {
        return repository.delete(id);
    }

    @Override
    public String validateNewContact(Map<String, String> allRequestParams) {
        return ServiceUtils.validateNewContact(allRequestParams);
    }
}
