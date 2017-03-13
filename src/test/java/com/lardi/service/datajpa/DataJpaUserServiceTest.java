package com.lardi.service.datajpa;

import com.lardi.model.User;
import com.lardi.service.AbstractUserServiceTest;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import javax.validation.ConstraintViolationException;

@ActiveProfiles("mysql")
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.save(new User(null, "", "Password", "Full Name")), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "Login", "", "Full Name")), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "Login", "Password", "")), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "Login123", "Password", "Full Name")), ConstraintViolationException.class);
    }
}