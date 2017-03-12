package com.lardi.service.datajpa;

import com.lardi.model.User;
import com.lardi.service.AbstractUserServiceTest;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;

@ActiveProfiles("mysql")
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.save(new User(null, "  ", "Password", "Full Name")), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "Login", "  ", "Full Name")), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "Login", "Password", "  ")), ConstraintViolationException.class);
    }
}