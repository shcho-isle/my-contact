package com.lardi.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.User;
import com.lardi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("jsonUserRepository")
public class JsonUserRepository extends AbstractJsonRepository implements UserRepository {
    private final String className = User.class.getName();

    @Override
    public User save(User user) {
        List<User> userList = getAllUsers();
        if (!checkRegisteredUsers(user.getId(), user.getFullName()))
            return null;
        if (userList == null)
            userList = new ArrayList<>();
        user.setId(userList.size() + 101);
        userList.add(user);
//        userRoleService.createRole(user);
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

    private boolean checkRegisteredUsers(Integer id, String fullName) {
        return get(id) == null & getUserFullName(fullName) == null;
    }

    public List<User> getAllUsers() {
        File f = getFilePath(className);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.err.println("ERROR: cannot create new file: " + className);
                e.printStackTrace();
            }
        }

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

    public void transactionWrite(List<User> userList) {
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
