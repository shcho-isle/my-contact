package com.lardi.service;

import com.lardi.model.Contact;
import com.lardi.repository.ContactRepository;
import com.lardi.repository.datajpa.CrudContactRepository;
import com.lardi.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("contactService")
public class ContactServiceImpl implements ContactService {

//    @Qualifier(value = "dataJpaContactRepository")
    @Qualifier(value = "jsonContactRepository")
    @Autowired
    private ContactRepository repository;

    private String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> list = new ArrayList<>();
        repository.getByUserLogin(getCurrentUser()).forEach(list::add);
        return list;
    }

    @Override
    public List<Contact> searchContactsByFirstName(String firstName) {
        List<Contact> contactList = getAllContacts();

        Comparator<Contact> groupByComparator = Comparator.comparing(Contact::getFirstName)
                .thenComparing(Contact::getLastName);
        return contactList
                .stream()
                .filter(e -> e.getFirstName().equalsIgnoreCase(firstName) || e.getLastName().equalsIgnoreCase(firstName))
                .sorted(groupByComparator)
                .collect(Collectors.toList());
    }

    @Override
    public Contact getContact(Integer id) throws Exception {
        List<Contact> contactList = getAllContacts();
        Optional<Contact> match
                = contactList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        if (match.isPresent()) {
            return match.get();
        } else {
            throw new Exception("The Contact id " + id + " not found");
        }
    }

    @Override
    public Integer saveContact(Contact contact) {
        return repository.save(contact).getId();
    }

    @Override
    public boolean updateContact(Contact contact) {
        return repository.update(contact);
    }

    @Override
    public boolean deleteContact(Integer id) {
        return repository.delete(id);
    }

    @Override
    public String validateNewContact(Map<String, String> allRequestParams) {
        return ServiceUtils.validateNewContact(allRequestParams);
    }
}
