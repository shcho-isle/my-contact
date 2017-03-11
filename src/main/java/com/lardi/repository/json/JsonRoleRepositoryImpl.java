package com.lardi.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.Role;
import com.lardi.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository("jsonRoleRepository")
public class JsonRoleRepositoryImpl extends AbstractJsonRepository implements RoleRepository {
    private final String className = Role.class.getName();

    @Override
    public void save(Role role) {
        List<Role> roleList = getAllRolesAndUsers();
        if (roleList == null)
            roleList = new ArrayList<>();
        role.setId(roleList.size() + 100);
        roleList.add(role);
        transactionWrite(roleList);
    }

    public List<Role> getAllRolesAndUsers() {
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
