package com.lardi.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.User;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository("userJsonRepository")
public class JsonUserRepository extends AbstractJsonRepository {
    private final String className = User.class.getName();

    public List<User> getAllUsers() throws IOException {
        File f = getFilePath(className);
        if (!f.exists()) {
            f.createNewFile();
        }
        Gson gson = new Gson();
        String jsonOutput = readJson(className);
        java.lang.reflect.Type listType = new TypeToken<List<User>>() {
        }.getType();
        return (List<User>) gson.fromJson(jsonOutput, listType);
    }

    public User getUserFullName(String fullName) throws IOException {
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
