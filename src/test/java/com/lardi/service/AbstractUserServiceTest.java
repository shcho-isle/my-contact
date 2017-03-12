package com.lardi.service;

import com.lardi.model.User;
import com.lardi.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import static com.lardi.UserTestData.*;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Test
    public void testSave() throws Exception {
        User newUser = new User(null, "new", "newPass", "new Full Name");
        User created = service.save(newUser);
        newUser.setId(created.getId());
        MATCHER.assertEquals(newUser, service.getByLogin("new"));
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateLoginSave() throws Exception {
        service.save(new User(null, "Serg", "newPass", "Duplicate"));
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

    @Test
    public void testGetByLogin() throws Exception {
        User user = service.getByLogin("Vano");
        MATCHER.assertEquals(USER1, user);
    }
}