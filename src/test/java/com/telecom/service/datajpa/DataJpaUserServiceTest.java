package com.telecom.service.datajpa;

import com.telecom.model.User;
import com.telecom.service.AbstractUserServiceTest;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import javax.validation.ConstraintViolationException;

@ActiveProfiles("mysql")
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void testValidation() throws Exception {
        //login validation
        validateRootCause(() -> service.save(new User(null, "логин", "Password", "Full Name")), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "ab", "Password", "Full Name")), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "<login>", "Password", "Full Name")), ConstraintViolationException.class);

        //password validation
        validateRootCause(() -> service.save(new User(null, "Login", "", "Full Name")), ConstraintViolationException.class);

        //fullName validation
        validateRootCause(() -> service.save(new User(null, "Login", "Password", "1234")), ConstraintViolationException.class);
    }
}