package com.lardi.service.datajpa;

import com.lardi.model.Contact;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import com.lardi.service.AbstractContactServiceTest;

import javax.validation.ConstraintViolationException;

import static com.lardi.UserTestData.VANO_ID;

@ActiveProfiles("mysql")
public class DataJpaContactServiceTest extends AbstractContactServiceTest {
    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.save(new Contact(null, "  ", "FirstName", "MiddleName", "MobilePhone", "", "", ""), VANO_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Contact(null, "LastName", "  ", "MiddleName", "MobilePhone", "", "", ""), VANO_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Contact(null, "LastName", "FirstName", "  ", "MobilePhone", "", "", ""), VANO_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Contact(null, "LastName", "FirstName", "MiddleName", "  ", "", "", ""), VANO_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Contact(null, "LastName", "FirstName", "MiddleName", "MobilePhone", "", "", "wrong email"), VANO_ID), ConstraintViolationException.class);
    }
}
