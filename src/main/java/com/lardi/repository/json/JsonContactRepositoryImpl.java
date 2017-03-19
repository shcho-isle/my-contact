package com.lardi.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.BaseEntity;
import com.lardi.model.Contact;
import com.lardi.model.User;
import com.lardi.repository.ContactRepository;
import com.lardi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Profile("json")
@Repository
public class JsonContactRepositoryImpl extends AbstractJsonRepository implements ContactRepository {

    @Autowired
    private UserRepository userRepository;

    private final String className = Contact.class.getName();

    private AtomicInteger counter;

    private AtomicInteger getCounter() {
        if (counter == null) {
            List<Contact> userList = getAllContacts();
            if (userList.isEmpty()) {
                this.counter = new AtomicInteger(0);
            } else {
                this.counter = new AtomicInteger(userList.stream().max(Comparator.comparing(BaseEntity::getId)).get().getId());
            }
        }
        return this.counter;
    }

    @Override
    public List<Contact> getAll(Integer userId) {
        List<Contact> contactList = getAllContacts();
        Comparator<Contact> groupByComparator = Comparator.comparing(Contact::getLastName);
        return contactList
                .stream()
                .filter(e -> e.getUserId().equals(userId))
                .sorted(groupByComparator)
                .collect(Collectors.toList());
    }

    @Override
    public Contact save(Contact contact, Integer userId) {
        List<Contact> contactList = getAllContacts();

        contact.setUserId(userId);

        if (contact.isNew()) {
            contact.setId(getCounter().incrementAndGet());
        }

        contactList.add(contact);
        transactionWrite(contactList);
        return contact;
    }

    @Override
    public Contact update(Contact contact, Integer userId) {
        List<Contact> contactList = getAllContacts();
        int matchIdx;
        User user = userRepository.get(userId);
        contact.setUserId(user.getId());
        Optional<Contact> match = contactList.stream()
                .filter(c -> c.getId().equals(contact.getId()) && c.getUserId().equals(userId))
                .findFirst();
        if (match.isPresent()) {
            matchIdx = contactList.indexOf(match.get());
            contactList.set(matchIdx, contact);
            transactionWrite(contactList);
            return contact;
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        List<Contact> contactList = getAllContacts();
        Predicate<Contact> contact = e -> (e.getId().equals(id) && e.getUserId().equals(userId));
        if (contactList.removeIf(contact)) {
            transactionWrite(contactList);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Contact> getFiltered(String filterRequest, Integer userId) {
        List<Contact> contactList = getAllContacts();
        return contactList
                .stream()
                .filter(e -> e.getUserId().equals(userId)
                        && (e.getFirstName().contains(filterRequest) || e.getLastName().contains(filterRequest) || e.getMobilePhone().contains(filterRequest)))
                .sorted(Comparator.comparing(Contact::getLastName))
                .collect(Collectors.toList());
    }

    @Override
    public Contact get(Integer id, Integer userId) {
        List<Contact> contactList = getAllContacts();
        return contactList
                .stream()
                .filter(e -> e.getId().equals(id) && e.getUserId().equals(userId))
                .findFirst().orElse(null);
    }

    private List<Contact> getAllContacts() {
        File f = getFilePath(className);
        checkIfExists(f, className);
        Gson gson = new Gson();
        String jsonOutput = readJson(className);
        java.lang.reflect.Type listType = new TypeToken<List<Contact>>() {
        }.getType();
        List<Contact> allContacts = gson.fromJson(jsonOutput, listType);
        return allContacts == null ? new ArrayList<>() : allContacts;
    }

    private void transactionWrite(List<Contact> contactList) {
        Gson gson = new Gson();
        java.lang.reflect.Type listType = new TypeToken<List<Contact>>() {
        }.getType();
        JsonElement contacts = gson.toJsonTree(contactList, listType);
        try {
            writeJson(String.valueOf(contacts), className);
        } catch (IndexOutOfBoundsException e) {
            File file = getFilePath(className);
            file.delete();
        }
    }
}
