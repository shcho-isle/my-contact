package com.lardi.service;

import com.lardi.model.Contact;
import org.junit.Assume;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.lardi.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static java.time.LocalDateTime.of;
import static com.lardi.ContactTestData.*;
import static com.lardi.UserTestData.SERG_ID;
import static com.lardi.UserTestData.VANO_ID;

public abstract class AbstractContactServiceTest extends AbstractServiceTest {

    @Autowired
    protected ContactService service;

    @Test
    public void testDelete() throws Exception {
        service.delete(VANO_CONTACT_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(VANO_CONTACT6, VANO_CONTACT5, VANO_CONTACT4, VANO_CONTACT3, VANO_CONTACT2), service.getAll(VANO_ID));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(SERG_CONTACT_ID);
    }

    @Test
    public void testSave() throws Exception {
        Contact created = getCreated();
        service.save(created);
        MATCHER.assertCollectionEquals(Arrays.asList(created, VANO_CONTACT6, VANO_CONTACT5, VANO_CONTACT4, VANO_CONTACT3, VANO_CONTACT2, VANO_CONTACT1), service.getAll(VANO_ID));
    }

    @Test
    public void testGet() throws Exception {
        Contact actual = service.get(VANO_CONTACT_ID, VANO_ID);
        MATCHER.assertEquals(VANO_CONTACT1, actual);
    }

    @Test
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(SERG_CONTACT_ID, VANO_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        Contact updated = getUpdated();
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(VANO_CONTACT_ID, VANO_ID));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + SERG_CONTACT_ID);
        service.update(SERG_CONTACT1);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(CONTACTS, service.getAll(VANO_ID));
    }
//
//    @Test
//    public void testGetBetween() throws Exception {
//        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1),
//                service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID));
//    }
//
//    @Test
//    public void testValidation() throws Exception {
//        Assume.assumeTrue(isJpaBased());
//        validateRootCause(() -> service.save(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "  ", 300), USER_ID), ConstraintViolationException.class);
//        validateRootCause(() -> service.save(new Meal(null, null, "Description", 300), USER_ID), ConstraintViolationException.class);
//        validateRootCause(() -> service.save(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 9), USER_ID), ConstraintViolationException.class);
//        validateRootCause(() -> service.save(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 5001), USER_ID), ConstraintViolationException.class);
//    }
}