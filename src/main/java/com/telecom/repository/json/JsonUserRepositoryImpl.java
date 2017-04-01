package com.telecom.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.telecom.model.BaseEntity;
import com.telecom.model.User;
import com.telecom.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Profile("json")
@Repository
public class JsonUserRepositoryImpl extends AbstractJsonRepository implements UserRepository {
    private final String className = User.class.getName();

    private AtomicInteger counter;

    private AtomicInteger getCounter() {
        List<User> userList = getAllUsers();

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
    public User save(User user) {
        List<User> userList = getAllUsers();

        if (get(user.getId()) != null || getByLogin(user.getLogin()) != null) {
            throw new DataIntegrityViolationException("User " + user.getLogin() + " already exists.");
        }

        if (user.isNew()) {
            user.setId(getCounter().incrementAndGet());
        }

        userList.add(user);
        transactionWrite(userList);
        return user;
    }

    @Override
    public User get(Integer id) {
        List<User> userList = getAllUsers();
        return userList.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public User getByLogin(String login) {
        List<User> userList = getAllUsers();
        return userList.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst().orElse(null);
    }

    private List<User> getAllUsers() {
        File f = getFilePath(className);
        checkIfExists(f, className);
        Gson gson = new Gson();
        String jsonOutput = readJson(className);
        java.lang.reflect.Type listType = new TypeToken<List<User>>() {
        }.getType();
        List<User> allUsers = gson.fromJson(jsonOutput, listType);
        return allUsers == null ? new ArrayList<>() : allUsers;
    }

    private void transactionWrite(List<User> userList) {
        Gson gson = new Gson();
        java.lang.reflect.Type listType = new TypeToken<List<User>>() {
        }.getType();
        JsonElement jsonTree = gson.toJsonTree(userList, listType);
        writeJson(String.valueOf(jsonTree), className);
    }
}
