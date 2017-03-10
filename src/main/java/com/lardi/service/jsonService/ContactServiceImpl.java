package com.lardi.service.jsonService;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.Contact;
import com.lardi.service.ContactService;
import com.lardi.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service("contactJsonService")
public class ContactServiceImpl implements ContactService {

    @Autowired
    private JsonFileService jsonFileService;

    private String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public List<Contact> getAllContacts() throws IOException {
        List<Contact> contactList = getAllBaseContacts();
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

    private List<Contact> getAllBaseContacts() throws IOException {
        File f = getFilePath();
        if (!f.exists()) {
            f.createNewFile();
        }
        Gson gson = new Gson();
        String jsonOutput = jsonFileService.readJson(Contact.class.getName());
        java.lang.reflect.Type listType = new TypeToken<List<Contact>>() {
        }.getType();
        return (List<Contact>) gson.fromJson(jsonOutput, listType);
    }

    @Override
    public Contact getContact(Integer id) throws Exception {
        List<Contact> contactList = getAllBaseContacts();
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
        List<Contact> contactList = getAllBaseContacts();
        if (contactList == null)
            contactList = new ArrayList<>();
        contact.setId(contactList.size() + 101);
        contactList.add(contact);
        transactionWrite(contactList);
        return contact.getId();
    }

    @Override
    public boolean updateContact(Contact contact) {
        try {
            List<Contact> contactList = getAllBaseContacts();
            int matchIdx;
            Optional<Contact> match = contactList.stream()
                    .filter(c -> c.getId().equals(contact.getId()))
                    .findFirst();
            if (match.isPresent()) {
                matchIdx = contactList.indexOf(match.get());
                contactList.set(matchIdx, contact);
            }
            transactionWrite(contactList);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean deleteContact(Integer id) {
        try {
            List<Contact> contactList = getAllBaseContacts();
            Predicate<Contact> contact = e -> e.getId().equals(id);
            if (contactList.removeIf(contact)) {
                transactionWrite(contactList);
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

    private void transactionWrite(List<Contact> contactList) {
        Gson gson = new Gson();
        java.lang.reflect.Type listType = new TypeToken<List<Contact>>() {
        }.getType();
        JsonElement contacts = gson.toJsonTree(contactList, listType);
        try {
            jsonFileService.write(String.valueOf(contacts), contactList.get(0).getClass().getName());
        } catch (IndexOutOfBoundsException e) {
            File file = getFilePath();
            file.delete();
        }
    }

    private File getFilePath() {
        Properties properties = ServiceUtils.getProperties();
        return new File(properties.getProperty("pathToFileFolder") + Contact.class.getName() + ".json");
    }
}