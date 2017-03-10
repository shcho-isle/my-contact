package service.jsonService;

import com.lardi.model.User;
import com.lardi.model.UserRole;
import com.lardi.service.UserService;
import com.lardi.service.jsonService.UserRoleService;
import com.lardi.service.jsonService.UserRoleServiceImpl;
import com.lardi.service.jsonService.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserRoleServiceImpl.class, UserServiceImpl.class})
public class UserRoleServiceTest {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userJsonService;

    @Before
    public void createUser() throws Exception {
        User user = new User("max", "1234577", "ultimax@ukr.net");
        userJsonService.save(user);
        System.out.println("Before");
    }

    @Test
    public void shouldCreateRole() throws IOException {
        User user = new User("max", "1234577", "ultimax@ukr.net");
        userRoleService.createRole(user);
        System.out.println("now1");
    }

    @Test
    public void shouldGetRoleByLogin() throws Exception {
        System.out.println("now2");
        UserRole returnedUserRole = userRoleService.getRoleByUserId(101);
        assertEquals(returnedUserRole.getRole(), "ROLE_ADMIN");
    }
}
