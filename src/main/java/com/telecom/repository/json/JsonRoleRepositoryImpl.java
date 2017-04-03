package com.telecom.repository.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.telecom.model.BaseEntity;
import com.telecom.model.Role;
import com.telecom.repository.RoleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Profile("json")
@Repository
public class JsonRoleRepositoryImpl extends AbstractJsonRepository implements RoleRepository {
    private final String className = Role.class.getName();

    private AtomicInteger counter;

    private AtomicInteger getCounter() {
        List<Role> userList = getAllRoles();

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
    public void save(Role role) {
        List<Role> roleList = getAllRoles();

        boolean roleExists = roleList.stream()
                .anyMatch(r -> r.getName().equals(role.getName()) && r.getUserId().equals(role.getUserId()));

        if (roleExists) {
            return;
        }

        if (role.isNew()) {
            role.setId(getCounter().incrementAndGet());
        }

        roleList.add(role);
        transactionWrite(roleList);
    }

    @Override
    public Set<Role> getAll(Integer userId) {
        List<Role> contactList = getAllRoles();
        return contactList
                .stream()
                .filter(e -> e.getUserId().equals(userId))
                .collect(Collectors.toSet());
    }

    private List<Role> getAllRoles() {
        File f = getFilePath(className);
        checkIfExists(f, className);

        Gson gson = new Gson();

        String jsonOutput = readJson(className);

        java.lang.reflect.Type listType = new TypeToken<List<Role>>() {
        }.getType();

        List<Role> allRoles = gson.fromJson(jsonOutput, listType);

        return allRoles == null ? new ArrayList<>() : allRoles;
    }

    private void transactionWrite(List<Role> roleList) {
        Gson gson = new Gson();
        java.lang.reflect.Type listType = new TypeToken<List<Role>>() {
        }.getType();
        JsonElement roles = gson.toJsonTree(roleList, listType);
        writeJson(String.valueOf(roles), className);
    }
}
