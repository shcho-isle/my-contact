package com.lardi.service.jsonService;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.User;
import com.lardi.util.ServiceUtils;
import com.lardi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service("userJsonService")
@ComponentScan(basePackageClasses = {JsonFileServiceImpl.class, UserRoleServiceImpl.class})
public class UserServiceImpl implements UserService {


    @Autowired
    private JsonFileService jsonFileService;

    @Autowired
    private UserRoleService userRoleService;


    private List<User> getAllUsers() throws IOException {
        File f = getFilePath();
        if (!f.exists()) {
            f.createNewFile();
        }
        Gson gson = new Gson();
        String jsonOutput = jsonFileService.readJson(User.class.getName());
        java.lang.reflect.Type listType = new TypeToken<List<User>>() {
        }.getType();
        return (List<User>) gson.fromJson(jsonOutput, listType);
    }

    @Override
    public User save(User user) throws IOException {
        List<User> userList = getAllUsers();
        if (!checkRegisteredUsers(user.getId(), user.getFullName()))
            return null;
        if (userList == null)
            userList = new ArrayList<>();
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setId(userList.size() + 101);
        userList.add(user);
        userRoleService.createRole(user);
        transactionWrite(userList);
        return user;
    }

    @Override
    public User get(Integer id) throws IOException {
        List<User> userList = getAllUsers();
        if (userList == null)
            return null;
        Optional<User> match = userList.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
        return match.orElse(null);
    }

    @Override
    public User getByLogin(String login) throws IOException {
        List<User> userList = getAllUsers();
        if (userList == null)
            return null;
        Optional<User> match = userList.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
        return match.orElse(null);
    }

    private User getUserFullName(String fullName) throws IOException {
        List<User> userList = getAllUsers();
        if (userList == null)
            return null;
        Optional<User> match
                = userList.stream()
                .filter(u -> u.getFullName().equals(fullName))
                .findFirst();
        return match.orElse(null);
    }

    public boolean checkRegisteredUsers(Integer id, String fullName) throws IOException {
        return get(id) == null & getUserFullName(fullName) == null;
    }

    private void transactionWrite(List<User> userList) {
        Gson gson = new Gson();
        java.lang.reflect.Type listType = new TypeToken<List<User>>() {
        }.getType();
        JsonElement users = gson.toJsonTree(userList, listType);
        try {
            jsonFileService.write(String.valueOf(users), userList.get(0).getClass().getName());
        } catch (IndexOutOfBoundsException e) {
            File file = getFilePath();
            file.delete();
        }
    }

    private File getFilePath() {
        Properties properties = ServiceUtils.getProperties();
        return new File(properties.getProperty("pathToFileFolder") + User.class.getName() + ".json");
    }
}
