package com.lardi.repository;

import com.lardi.model.Contact;

import java.util.List;

public interface ContactRepository {
    List<Contact> getByUserLogin(String login);

    Contact save(Contact contact);

    boolean update(Contact contact);

    boolean delete(Integer id);

    List<Contact> getFiltered(String filterRequest, Integer userId);

    // null if contact do not belong to userId
    Contact get(Integer id, Integer userId);
}
