package com.lardi.service;

import com.lardi.model.Contact;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ContactService {
    List<Contact> getAllContacts() throws IOException;

    List<Contact> searchContactsByFirstName(String name) throws IOException;

    Contact getContact(Integer id) throws Exception;

    Integer saveContact(Contact contact) throws IOException;

    boolean updateContact(Contact customer);

    boolean deleteContact(Integer id);

    String validateNewContact(Map<String, String> allRequestParams);
}
