package com.lardi.service;

import com.lardi.model.Contact;
import com.lardi.repository.ContactRepository;
import com.lardi.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository repository;

    @Override
    public List<Contact> getAll() {
        List<Contact> list = new ArrayList<>();
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        repository.getByUserLogin(login).forEach(list::add);
        return list;
    }

    @Override
    public List<Contact> searchByFirstName(String firstName) {
        List<Contact> contactList = getAll();

        Comparator<Contact> groupByComparator = Comparator.comparing(Contact::getFirstName)
                .thenComparing(Contact::getLastName);
        return contactList
                .stream()
                .filter(e -> e.getFirstName().equalsIgnoreCase(firstName) || e.getLastName().equalsIgnoreCase(firstName))
                .sorted(groupByComparator)
                .collect(Collectors.toList());
    }

    @Override
    public Contact get(Integer id) throws Exception {
        List<Contact> contactList = getAll();
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
