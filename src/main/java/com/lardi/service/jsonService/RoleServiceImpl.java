package com.lardi.service.jsonService;

import com.lardi.model.User;
import com.lardi.model.Role;
import com.lardi.repository.json.JsonRoleRepository;
import com.lardi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserService userService;

    @Autowired
    private JsonRoleRepository jsonRoleRepository;

    @Override
    public void createRole(User user) throws IOException {
        List<Role> roleList = jsonRoleRepository.getAllRolesAndUsers();
        if (roleList == null)
            roleList = new ArrayList<>();
        Role role = new Role(user.getId());
        role.setId(roleList.size() + 100);
        roleList.add(role);
        jsonRoleRepository.transactionWrite(roleList);
    }

    @Override
    public Role getRoleByUserId(Integer userId) throws Exception {
        User user = userService.get(userId);
        List<Role> roleList = jsonRoleRepository.getAllRolesAndUsers();
        Optional<Role> match
                = roleList.stream()
                .filter(ur -> ur.getUserId().equals(user.getId()))
                .findFirst();
        if (match.isPresent()) {
            return match.get();
        } else {
            throw new Exception("The Role of  " + userId + " not found");
        }
    }
}
