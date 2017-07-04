package com.telecom.service;

import com.telecom.model.Contact;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.telecom.util.exception.NotFoundException;

import java.util.Arrays;

import static com.telecom.ContactTestData.*;
import static com.telecom.UserTestData.USER1_ID;

public abstract class AbstractContactServiceTest extends AbstractServiceTest {

    @Autowired
    protected ContactService service;

    @Test
    public void testDelete() throws Exception {
        service.delete(USER1_CONTACT_ID, USER1_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER1_CONTACT2, USER1_CONTACT3, USER1_CONTACT4, USER1_CONTACT5, USER1_CONTACT6), service.getAll(USER1_ID));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(USER2_CONTACT_ID, USER1_ID);
    }

    @Test
    public void testSave() throws Exception {
        Contact created = getCreated();
        service.save(created, USER1_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(created, USER1_CONTACT1, USER1_CONTACT2, USER1_CONTACT3, USER1_CONTACT4, USER1_CONTACT5, USER1_CONTACT6), service.getAll(USER1_ID));
    }

    @Test
    public void testGet() throws Exception {
        Contact actual = service.get(USER1_CONTACT_ID, USER1_ID);
        MATCHER.assertEquals(USER1_CONTACT1, actual);
    }

    @Test
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(USER2_CONTACT_ID, USER1_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        Contact updated = getUpdated();
        service.update(updated, USER1_ID);
        MATCHER.assertEquals(updated, service.get(USER1_CONTACT_ID, USER1_ID));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + USER2_CONTACT_ID);
        service.update(USER2_CONTACT1, USER1_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(CONTACTS, service.getAll(USER1_ID));
    }

    @Test
    public void testGetFiltered() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(USER1_CONTACT1, USER1_CONTACT3),
                service.getFiltered("as", USER1_ID));
    }
}