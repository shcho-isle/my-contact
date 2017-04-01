package com.telecom.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.telecom.model.BaseEntity;
import com.telecom.model.Contact;
import com.telecom.repository.ContactRepository;
import com.telecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Profile("json")
@Repository
public class JsonContactRepositoryImpl extends AbstractJsonRepository implements ContactRepository {

    private final UserRepository userRepository;

    private final String className = Contact.class.getName();

    private AtomicInteger counter;

    @Autowired
    public JsonContactRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private AtomicInteger getCounter() {
        List<Contact> userList = getAllContacts();
        if (userList.isEmpty()) {
            this.counter = new AtomicInteger(0);
        } else {
            if (counter == null) {
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
        if (userRepository.get(userId) == null) {
            throw new DataIntegrityViolationException("User with id=" + userId + " does not exist.");
        }
        contact.setUserId(userId);

        List<Contact> contactList = getAllContacts();

        if (contact.isNew()) {
            contact.setId(getCounter().incrementAndGet());
            contactList.add(contact);
        } else {
            int matchIdx;
            Optional<Contact> match = contactList.stream()
                    .filter(c -> c.getId().equals(contact.getId()) && c.getUserId().equals(userId))
                    .findFirst();
            if (match.isPresent()) {
                matchIdx = contactList.indexOf(match.get());
                contactList.set(matchIdx, contact);
            } else {
                return null;
            }
        }

        transactionWrite(contactList);
        return contact;
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
        JsonElement jsonTree = gson.toJsonTree(contactList, listType);
        writeJson(String.valueOf(jsonTree), className);
    }
}
