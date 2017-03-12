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
@Repository("jsonContactRepository")
public class JsonContactRepositoryImpl extends AbstractJsonRepository implements ContactRepository {

    @Autowired
    private UserRepository userRepository;

    private final String className = Contact.class.getName();

    @Override
    public List<Contact> getByUserLogin(String login) {
        List<Contact> contactList = getAllBaseContacts();
        if (contactList == null)
            return Collections.emptyList();
        Comparator<Contact> groupByComparator = Comparator.comparing(Contact::getFirstName)
                .thenComparing(Contact::getLastName);
        return contactList
                .stream()
                .filter(e -> e.getUserLogin().equalsIgnoreCase(login))
                .sorted(groupByComparator)
                .collect(Collectors.toList());
    }

    @Override
    public Contact save(Contact contact) {
        List<Contact> contactList = getAllBaseContacts();
        if (contactList == null)
            contactList = new ArrayList<>();
        contact.setId(contactList.size() + 101);
        contactList.add(contact);
        transactionWrite(contactList);
        return contact;
    }

    @Override
    public boolean update(Contact contact) {
        List<Contact> contactList = getAllBaseContacts();
        int matchIdx;
        Optional<Contact> match = contactList.stream()
                .filter(c -> c.getId().equals(contact.getId()))
                .findFirst();
        if (match.isPresent()) {
            matchIdx = contactList.indexOf(match.get());
            contactList.set(matchIdx, contact);
            transactionWrite(contactList);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        List<Contact> contactList = getAllBaseContacts();
        Predicate<Contact> contact = e -> e.getId().equals(id);
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
        User user = userRepository.get(userId);
        return contactList
                .stream()
                .filter(e -> e.getUserLogin().equalsIgnoreCase(user.getLogin())
                        && (e.getFirstName().contains(filterRequest) || e.getLastName().contains(filterRequest) || e.getMobilePhone().contains(filterRequest)))
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
        User user = userRepository.get(userId);
        return contact != null && Objects.equals(contact.getUserLogin(), user.getLogin()) ? contact : null;
    }

    public List<Contact> getAllBaseContacts() {
        File f = getFilePath(className);
        checkIfExists(f, className);

        Gson gson = new Gson();

        String jsonOutput = readJson(className);

        java.lang.reflect.Type listType = new TypeToken<List<Contact>>() {}.getType();

        return (List<Contact>) gson.fromJson(jsonOutput, listType);
    }

    public void transactionWrite(List<Contact> contactList) {
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
