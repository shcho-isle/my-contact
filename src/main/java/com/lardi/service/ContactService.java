package com.lardi.service;

import com.lardi.model.Contact;

import java.util.List;
import java.util.Map;

public interface ContactService {
    List<Contact> getAll(Integer userId);

    List<Contact> getFiltered(String filterRequest, Integer userId);

    Contact get(Integer id, Integer userId);

    Contact save(Contact contact, Integer userId);

    Contact update(Contact customer, Integer userId);

    void delete(int id, Integer userId);

    String validateNewContact(Map<String, String> allRequestParams);
}
