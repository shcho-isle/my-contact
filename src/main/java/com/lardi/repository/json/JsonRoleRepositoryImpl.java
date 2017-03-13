package com.lardi.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.Role;
import com.lardi.repository.RoleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Profile("json")
@Repository("jsonRoleRepository")
public class JsonRoleRepositoryImpl extends AbstractJsonRepository implements RoleRepository {
    private final String className = Role.class.getName();

    @Override
    public void save(Role role) {
        List<Role> roleList = getAllRolesAndUsers();
        if (roleList == null)
            roleList = new ArrayList<>();
        role.setId(roleList.size() + 1);
        roleList.add(role);
        transactionWrite(roleList);
    }

    public List<Role> getAllRolesAndUsers() {
        File f = getFilePath(className);
        checkIfExists(f, className);

        Gson gson = new Gson();

        String jsonOutput = readJson(className);

        java.lang.reflect.Type listType = new TypeToken<List<Role>>() {}.getType();

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
