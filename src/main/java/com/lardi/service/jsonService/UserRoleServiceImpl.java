package com.lardi.service.jsonService;

import com.lardi.model.User;
import com.lardi.model.UserRole;
import com.lardi.repository.json.JsonRoleRepository;
import com.lardi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserService userService;

    @Autowired
    private JsonRoleRepository jsonRoleRepository;

    @Override
    public void createRole(User user) throws IOException {
        List<UserRole> userRoleList = jsonRoleRepository.getAllRolesAndUsers();
        if (userRoleList == null)
            userRoleList = new ArrayList<>();
        UserRole userRole = new UserRole(user.getId());
        userRole.setId(userRoleList.size() + 100);
        userRoleList.add(userRole);
        jsonRoleRepository.transactionWrite(userRoleList);
    }

    @Override
    public UserRole getRoleByUserId(Integer userId) throws Exception {
        User user = userService.get(userId);
        List<UserRole> userRoleList = jsonRoleRepository.getAllRolesAndUsers();
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
}
