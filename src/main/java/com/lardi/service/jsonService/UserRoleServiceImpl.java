package com.lardi.service.jsonService;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lardi.model.User;
import com.lardi.model.UserRole;
import com.lardi.util.ServiceUtils;
import com.lardi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
@ComponentScan(basePackageClasses = JsonFileServiceImpl.class)
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private JsonFileService jsonFileService;

    @Autowired
    private UserService userJsonService;

    private List<UserRole> getAllRolesAndUsers() throws IOException {
        File f = getFilePath();
        if (!f.exists()) {
            f.createNewFile();
        }
        Gson gson = new Gson();
        String jsonOutput = jsonFileService.readJson(UserRole.class.getName());
        java.lang.reflect.Type listType = new TypeToken<List<UserRole>>() {
        }.getType();
        return (List<UserRole>) gson.fromJson(jsonOutput, listType);
    }

    @Override
    public void createRole(User user) throws IOException {
        List<UserRole> userRoleList = getAllRolesAndUsers();
        if (userRoleList == null)
            userRoleList = new ArrayList<>();
        UserRole userRole = new UserRole(user.getId());
        userRole.setId(userRoleList.size() + 100);
        userRoleList.add(userRole);
        transactionWrite(userRoleList);
    }

    @Override
    public UserRole getRoleByUserId(Integer userId) throws Exception {
        User user = userJsonService.get(userId);
        System.out.println("+++++++++++++++++++++++++++++++++" + user);
        List<UserRole> userRoleList = getAllRolesAndUsers();
        Optional<UserRole> match
                = userRoleList.stream()
                .filter(ur -> ur.getUserId().equals(user.getId()))
                .findFirst();
        if (match.isPresent()) {
            return match.get();
        } else {
            throw new Exception("The UserRole of  " + userId + " not found");
        }
    }

    private void transactionWrite(List<UserRole> userRoleList) {
        Gson gson = new Gson();
        java.lang.reflect.Type listType = new TypeToken<List<UserRole>>() {
        }.getType();
        JsonElement userRoles = gson.toJsonTree(userRoleList, listType);
        try {
            jsonFileService.write(String.valueOf(userRoles), userRoleList.get(0).getClass().getName());
        } catch (IndexOutOfBoundsException e) {
            File file = getFilePath();
            file.delete();
        }
    }

    private File getFilePath() {
        Properties properties = ServiceUtils.getProperties();
        return new File(properties.getProperty("pathToFileFolder") + UserRole.class.getName() + ".json");
    }
}
