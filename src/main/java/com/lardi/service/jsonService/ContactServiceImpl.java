package com.lardi.service.jsonService;

import com.lardi.model.Contact;
import com.lardi.repository.json.JsonContactRepository;
import com.lardi.service.ContactService;
import com.lardi.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service("contactJsonService")
public class ContactServiceImpl implements ContactService {

    //    @Qualifier(value = "contactJsonService") //Раскоментировать для использования JSON File
    @Autowired
    private JsonContactRepository jsonContactRepository;

    private String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public List<Contact> getAllContacts() throws IOException {
        List<Contact> contactList = jsonContactRepository.getAllBaseContacts();
        if (contactList == null)
            return null;
        Comparator<Contact> groupByComparator = Comparator.comparing(Contact::getFirstName)
                .thenComparing(Contact::getLastName);
        return contactList
                .stream()
                .filter(e -> e.getUserLogin().equalsIgnoreCase(getCurrentUser()))
                .sorted(groupByComparator)
                .collect(Collectors.toList());
    }

    @Override
    public List<Contact> searchContactsByFirstName(String firstName) throws IOException {
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
        List<Contact> contactList = jsonContactRepository.getAllBaseContacts();
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
    public Integer saveContact(Contact contact) throws IOException {
        List<Contact> contactList = jsonContactRepository.getAllBaseContacts();
        if (contactList == null)
            contactList = new ArrayList<>();
        contact.setId(contactList.size() + 101);
        contactList.add(contact);
        jsonContactRepository.transactionWrite(contactList);
        return contact.getId();
    }

    @Override
    public boolean updateContact(Contact contact) {
        try {
            List<Contact> contactList = jsonContactRepository.getAllBaseContacts();
            int matchIdx;
            Optional<Contact> match = contactList.stream()
                    .filter(c -> c.getId().equals(contact.getId()))
                    .findFirst();
            if (match.isPresent()) {
                matchIdx = contactList.indexOf(match.get());
                contactList.set(matchIdx, contact);
            }
            jsonContactRepository.transactionWrite(contactList);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean deleteContact(Integer id) {
        try {
            List<Contact> contactList = jsonContactRepository.getAllBaseContacts();
            Predicate<Contact> contact = e -> e.getId().equals(id);
            if (contactList.removeIf(contact)) {
                jsonContactRepository.transactionWrite(contactList);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String validateNewContact(Map<String, String> allRequestParams) {
        return ServiceUtils.validateNewContact(allRequestParams);
    }
}