package com.lardi.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.User;
import com.lardi.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Profile("json")
@Repository("jsonUserRepository")
public class JsonUserRepositoryImpl extends AbstractJsonRepository implements UserRepository {
    private final String className = User.class.getName();

    @Override
    public User save(User user) {
        List<User> userList = getAllUsers();
        if (get(user.getId()) != null || getByLogin(user.getLogin()) != null)
            throw new DataIntegrityViolationException("User " + user.getLogin() + " already exists.");
        if (userList == null)
            userList = new ArrayList<>();
        user.setId(userList.size() + 1);
        userList.add(user);
        transactionWrite(userList);
        return user;
    }

    @Override
    public User get(Integer id) {
        List<User> userList = getAllUsers();
        if (userList == null)
            return null;
        Optional<User> match = userList.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
        return match.orElse(null);
    }

    @Override
    public User getByLogin(String login) {
        List<User> userList = getAllUsers();
        if (userList == null)
            return null;
        Optional<User> match = userList.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
        return match.orElse(null);
    }

    private List<User> getAllUsers() {
        File f = getFilePath(className);
        checkIfExists(f, className);
        Gson gson = new Gson();
        String jsonOutput = readJson(className);
        java.lang.reflect.Type listType = new TypeToken<List<User>>() {}.getType();
        return (List<User>) gson.fromJson(jsonOutput, listType);
    }

    public User getUserFullName(String fullName) {
        List<User> userList = getAllUsers();
        if (userList == null)
            return null;
        Optional<User> match
                = userList.stream()
                .filter(u -> u.getFullName().equals(fullName))
                .findFirst();
        return match.orElse(null);
    }

    private void transactionWrite(List<User> userList) {
        Gson gson = new Gson();
        java.lang.reflect.Type listType = new TypeToken<List<User>>() {
        }.getType();
        JsonElement users = gson.toJsonTree(userList, listType);
        try {
            writeJson(String.valueOf(users), className);
        } catch (IndexOutOfBoundsException e) {
            File file = getFilePath(className);
            file.delete();
        }
    }
}
