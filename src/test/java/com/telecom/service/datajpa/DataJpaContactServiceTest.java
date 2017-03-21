package com.telecom.service.datajpa;

import com.telecom.model.Contact;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import com.telecom.service.AbstractContactServiceTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import javax.validation.ConstraintViolationException;

import static com.telecom.UserTestData.VANO_ID;

@ActiveProfiles("mysql")
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DataJpaContactServiceTest extends AbstractContactServiceTest {
    @Test
    public void testValidation() throws Exception {
        //lastName validation
        validateRootCause(() -> service.save(new Contact(null, "", "FirstName", "MiddleName", "+38(063)1234567", "", "", ""), VANO_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Contact(null, "123", "FirstName", "MiddleName", "+38(063)1234567", "", "", ""), VANO_ID), ConstraintViolationException.class);

        //firstName validation
        validateRootCause(() -> service.save(new Contact(null, "LastName", "", "MiddleName", "+38(063)1234567", "", "", ""), VANO_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Contact(null, "LastName", "123", "MiddleName", "+38(063)1234567", "", "", ""), VANO_ID), ConstraintViolationException.class);

        //middleName validation
        validateRootCause(() -> service.save(new Contact(null, "LastName", "FirstName", "", "+38(063)1234567", "", "", ""), VANO_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Contact(null, "LastName", "FirstName", "123", "+38(063)1234567", "", "", ""), VANO_ID), ConstraintViolationException.class);

        //mobilePhone validation
        validateRootCause(() -> service.save(new Contact(null, "LastName", "FirstName", "MiddleName", "", "", "", ""), VANO_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Contact(null, "LastName", "FirstName", "MiddleName", "+99(063)1234567", "", "", ""), VANO_ID), ConstraintViolationException.class);

        //homePhone validation
        validateRootCause(() -> service.save(new Contact(null, "LastName", "FirstName", "MiddleName", "+38(063)1234567", "+99(063)1234567", "", ""), VANO_ID), ConstraintViolationException.class);

        //email validation
        validateRootCause(() -> service.save(new Contact(null, "LastName", "FirstName", "MiddleName", "+38(063)1234567", "", "", "bad email"), VANO_ID), ConstraintViolationException.class);
    }
}
