package com.lardi.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.UserRole;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class JsonRoleRepository extends AbstractJsonRepository {
    private final String className = UserRole.class.getName();

    public List<UserRole> getAllRolesAndUsers() throws IOException {
        File f = getFilePath(className);
        if (!f.exists()) {
            f.createNewFile();
        }
        Gson gson = new Gson();
        String jsonOutput = readJson(className);
        java.lang.reflect.Type listType = new TypeToken<List<UserRole>>() {
        }.getType();
        return (List<UserRole>) gson.fromJson(jsonOutput, listType);
    }

    public void transactionWrite(List<UserRole> userRoleList) {
        Gson gson = new Gson();
        java.lang.reflect.Type listType = new TypeToken<List<UserRole>>() {
        }.getType();
        JsonElement userRoles = gson.toJsonTree(userRoleList, listType);
        try {
            writeJson(String.valueOf(userRoles), className);
        } catch (IndexOutOfBoundsException e) {
            File file = getFilePath(className);
            file.delete();
        }
    }
}
