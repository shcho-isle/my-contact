package com.lardi.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.Contact;
import com.lardi.model.User;
import com.lardi.repository.ContactRepository;
import com.lardi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Profile("json")
@Repository
public class JsonContactRepositoryImpl extends AbstractJsonRepository implements ContactRepository {

    @Autowired
    private UserRepository userRepository;

    private final String className = Contact.class.getName();

    @Override
    public List<Contact> getAll(Integer userId) {
        List<Contact> contactList = getAllBaseContacts();
        if (contactList == null)
            return Collections.emptyList();
        Comparator<Contact> groupByComparator = Comparator.comparing(Contact::getLastName);
        return contactList
                .stream()
                .filter(e -> e.getUserId().equals(userId))
                .sorted(groupByComparator)
                .collect(Collectors.toList());
    }

    @Override
    public Contact save(Contact contact, Integer userId) {
        List<Contact> contactList = getAllBaseContacts();
        if (contactList == null)
            contactList = new ArrayList<>();
        contact.setUserId(userId);
        contact.setId(contactList.size() + 1);
        contactList.add(contact);
        transactionWrite(contactList);
        return contact;
    }

    @Override
    public Contact update(Contact contact, Integer userId) {
        List<Contact> contactList = getAllBaseContacts();
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
        List<Contact> contactList = getAllBaseContacts();
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
        List<Contact> contactList = getAllBaseContacts();
        if (contactList == null)
            return Collections.emptyList();
        Comparator<Contact> groupByComparator = Comparator.comparing(Contact::getLastName);
        return contactList
                .stream()
                .filter(e -> e.getUserId().equals(userId)
                        && (e.getFirstName().contains(filterRequest) || e.getLastName().contains(filterRequest) || e.getMobilePhone().contains(filterRequest)))
                .sorted(groupByComparator)
                .collect(Collectors.toList());
    }

    @Override
    public Contact get(Integer id, Integer userId) {
        List<Contact> contactList = getAllBaseContacts();
        if (contactList == null)
            return null;
        Contact contact = contactList
                .stream()
                .filter(e -> e.getId().equals(id))
                .findFirst().orElse(null);
        return contact != null && Objects.equals(contact.getUserId(), userId) ? contact : null;
    }

    private List<Contact> getAllBaseContacts() {
        File f = getFilePath(className);
        checkIfExists(f, className);

        Gson gson = new Gson();

        String jsonOutput = readJson(className);

        java.lang.reflect.Type listType = new TypeToken<List<Contact>>() {
        }.getType();

        return (List<Contact>) gson.fromJson(jsonOutput, listType);
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
