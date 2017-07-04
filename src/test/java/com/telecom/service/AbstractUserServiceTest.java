package com.telecom.service;

import com.telecom.model.User;
import com.telecom.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import static com.telecom.UserTestData.*;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Test
    public void testSave() throws Exception {
        User newUser = new User(null, "new", "newPass", "new Full Name");
        User created = service.save(newUser);
        newUser.setId(created.getId());
        MATCHER.assertEquals(newUser, service.get(USER1_ID + 2));
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateLoginSave() throws Exception {
        service.save(new User(null, "use2", "newPass", "Duplicate"));
    }

    @Test
    public void testGet() throws Exception {
        User user = service.get(USER1_ID);
        MATCHER.assertEquals(USER1, user);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(10);
    }
}