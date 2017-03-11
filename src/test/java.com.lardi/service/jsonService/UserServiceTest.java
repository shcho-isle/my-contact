package service.jsonService;

import com.lardi.model.User;
import com.lardi.service.UserService;
import com.lardi.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(classes = UserServiceImpl.class)
})
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void shouldCreateUser() throws Exception {
        User user = new User("max", "1234577", "ultimax@ukr.net");
        service.save(user);
        User user2 = new User("kris", "1234577", "mysyundra@ya.ru");
        service.save(user2);
    }

    @Test
    public void shouldGetUser() throws Exception {
        User user = new User("max", "$2a$10$tbmoYsawbPFAYnnWNLz9SezgX.FcgoSMVMba8TtPqxepMltLc4i/m", "ultimax@ukr.net");
        User returnedUser = service.get(101);
        assertEquals(user.getLogin(), returnedUser.getLogin());
    }
}
