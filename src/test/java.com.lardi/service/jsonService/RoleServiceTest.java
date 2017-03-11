package service.jsonService;

import com.lardi.model.User;
import com.lardi.model.Role;
import com.lardi.service.UserService;
import com.lardi.service.UserServiceImpl;
import com.lardi.service.jsonService.RoleService;
import com.lardi.service.jsonService.RoleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RoleServiceImpl.class, UserServiceImpl.class})
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService service;

    @Before
    public void createUser() throws Exception {
        User user = new User("max", "1234577", "ultimax@ukr.net");
        service.save(user);
        System.out.println("Before");
    }

    @Test
    public void shouldCreateRole() throws IOException {
        User user = new User("max", "1234577", "ultimax@ukr.net");
        roleService.createRole(user);
        System.out.println("now1");
    }

    @Test
    public void shouldGetRoleByLogin() throws Exception {
        System.out.println("now2");
        Role returnedRole = roleService.getRoleByUserId(101);
        assertEquals(returnedRole.getRole(), "ROLE_ADMIN");
    }
}
