package com.telecom.repository;

import com.telecom.model.Contact;

import java.util.List;

public interface ContactRepository {
    // ORDERED lastName
    List<Contact> getAll(Integer userId);

    Contact save(Contact contact, Integer userId);

    // false if contact do not belong to userId
    boolean delete(Integer id, Integer userId);

    // ORDERED lastName
    List<Contact> getFiltered(String filterRequest, Integer userId);

    // null if contact do not belong to userId
    Contact get(Integer id, Integer userId);
}
