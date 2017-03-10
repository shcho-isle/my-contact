package com.lardi.service.jsonService;

import com.lardi.model.User;
import com.lardi.repository.json.JsonUserRepository;
import com.lardi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("userJsonService")
@ComponentScan(basePackageClasses = UserRoleServiceImpl.class)
public class UserServiceImpl implements UserService {

    //    @Qualifier(value = "userJsonRepository") //Раскоментировать для использования JSON File
    @Autowired
    private JsonUserRepository jsonUserRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public User save(User user) throws IOException {
        List<User> userList = jsonUserRepository.getAllUsers();
        if (!checkRegisteredUsers(user.getId(), user.getFullName()))
            return null;
        if (userList == null)
            userList = new ArrayList<>();
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setId(userList.size() + 101);
        userList.add(user);
        userRoleService.createRole(user);
        jsonUserRepository.transactionWrite(userList);
        return user;
    }

    @Override
    public User get(Integer id) throws IOException {
        List<User> userList = jsonUserRepository.getAllUsers();
        if (userList == null)
            return null;
        Optional<User> match = userList.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
        return match.orElse(null);
    }

    @Override
    public User getByLogin(String login) throws IOException {
        List<User> userList = jsonUserRepository.getAllUsers();
        if (userList == null)
            return null;
        Optional<User> match = userList.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
        return match.orElse(null);
    }

    private boolean checkRegisteredUsers(Integer id, String fullName) throws IOException {
        return get(id) == null & jsonUserRepository.getUserFullName(fullName) == null;
    }
}
