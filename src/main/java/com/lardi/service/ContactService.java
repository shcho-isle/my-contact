package com.lardi.service;

import com.lardi.model.Contact;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ContactService {
    List<Contact> getAll() throws IOException;

    List<Contact> searchByFirstName(String name) throws IOException;

    Contact get(Integer id) throws Exception;

    Integer save(Contact contact) throws IOException;

    boolean update(Contact customer);

    boolean delete(Integer id);

    String validateNewContact(Map<String, String> allRequestParams);
}
