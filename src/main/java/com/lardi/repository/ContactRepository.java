package com.lardi.repository;

import com.lardi.model.Contact;

import java.util.List;

public interface ContactRepository {
    // ORDERED lastName
    List<Contact> getAll(Integer userId);

    Contact save(Contact contact);

    boolean update(Contact contact);

    // false if meal do not belong to userId
    boolean delete(Integer id, Integer userId);

    // ORDERED lastName
    List<Contact> getFiltered(String filterRequest, Integer userId);

    // null if contact do not belong to userId
    Contact get(Integer id, Integer userId);
}
