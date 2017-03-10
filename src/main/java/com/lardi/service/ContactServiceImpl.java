package com.lardi.service;

import com.lardi.model.Contact;
import com.lardi.repository.datajpa.CrudContactRepository;
import com.lardi.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("contactService")
public class ContactServiceImpl implements ContactService {

    @Autowired
    private CrudContactRepository crudContactRepository;

    private String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<Contact> getAllContacts() {
        List<Contact> list = new ArrayList<>();
        crudContactRepository.findByUserLogin(getCurrentUser()).forEach(list::add);
        return list;
    }

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

    public Integer saveContact(Contact contact) {
        return crudContactRepository.save(contact).getId();
    }

    public boolean updateContact(Contact customer) {
        return crudContactRepository.save(customer).getId().equals(customer.getId());
    }

    public boolean deleteContact(Integer id) {
        crudContactRepository.delete(id);
        return !crudContactRepository.exists(id);
    }

    public String validateNewContact(Map<String, String> allRequestParams) {
        return ServiceUtils.validateNewContact(allRequestParams);
    }
}
