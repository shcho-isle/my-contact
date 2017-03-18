package com.lardi.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.BaseEntity;
import com.lardi.model.Role;
import com.lardi.repository.RoleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Profile("json")
@Repository
public class JsonRoleRepositoryImpl extends AbstractJsonRepository implements RoleRepository {
    private final String className = Role.class.getName();

    private AtomicInteger counter;

    private AtomicInteger getCounter() {
        if (counter == null) {
            List<Role> userList = getAllRoles();
            if (userList == null) {
                this.counter = new AtomicInteger(0);
            } else {
                this.counter = new AtomicInteger(userList.stream().max(Comparator.comparing(BaseEntity::getId)).get().getId());
            }
        }
        return this.counter;
    }

    @Override
    public void save(Role role) {
        List<Role> roleList = getAllRoles();

        if (roleList == null) {
            roleList = new ArrayList<>();
        }

        if (role.isNew()) {
            role.setId(getCounter().incrementAndGet());
        }

        roleList.add(role);
        transactionWrite(roleList);
    }

    private List<Role> getAllRoles() {
        File f = getFilePath(className);
        checkIfExists(f, className);

        Gson gson = new Gson();

        String jsonOutput = readJson(className);

        java.lang.reflect.Type listType = new TypeToken<List<Role>>() {
        }.getType();

        return (List<Role>) gson.fromJson(jsonOutput, listType);
    }

    private void transactionWrite(List<Role> roleList) {
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
