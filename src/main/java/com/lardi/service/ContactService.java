package com.lardi.service;

import com.lardi.model.Contact;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ContactService {
    List<Contact> getAll(Integer userId);

    List<Contact> getFiltered(String filterRequest, Integer userId);

    Contact get(Integer id, Integer userId);

    Integer save(Contact contact);

    boolean update(Contact customer);

    boolean delete(Integer id);

    String validateNewContact(Map<String, String> allRequestParams);
}
