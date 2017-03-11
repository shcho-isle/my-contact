package com.lardi.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.Role;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class JsonRoleRepository extends AbstractJsonRepository {
    private final String className = Role.class.getName();

    public List<Role> getAllRolesAndUsers() throws IOException {
        File f = getFilePath(className);
        if (!f.exists()) {
            f.createNewFile();
        }
        Gson gson = new Gson();
        String jsonOutput = readJson(className);
        java.lang.reflect.Type listType = new TypeToken<List<Role>>() {
        }.getType();
        return (List<Role>) gson.fromJson(jsonOutput, listType);
    }

    public void transactionWrite(List<Role> roleList) {
        Gson gson = new Gson();
        java.lang.reflect.Type listType = new TypeToken<List<Role>>() {
        }.getType();
        JsonElement roles = gson.toJsonTree(roleList, listType);
        try {
            writeJson(String.valueOf(roles), className);
        } catch (IndexOutOfBoundsException e) {
            File file = getFilePath(className);
            file.delete();
        }
    }
}
