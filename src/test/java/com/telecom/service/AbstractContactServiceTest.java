package com.telecom.service;

import com.telecom.model.Contact;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.telecom.util.exception.NotFoundException;

import java.util.Arrays;

import static com.telecom.ContactTestData.*;
import static com.telecom.UserTestData.VANO_ID;

public abstract class AbstractContactServiceTest extends AbstractServiceTest {

    @Autowired
    protected ContactService service;

    @Test
    public void testDelete() throws Exception {
        service.delete(VANO_CONTACT_ID, VANO_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(VANO_CONTACT2, VANO_CONTACT3, VANO_CONTACT4, VANO_CONTACT5, VANO_CONTACT6), service.getAll(VANO_ID));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(SERG_CONTACT_ID, VANO_ID);
    }

    @Test
    public void testSave() throws Exception {
        Contact created = getCreated();
        service.save(created, VANO_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(created, VANO_CONTACT1, VANO_CONTACT2, VANO_CONTACT3, VANO_CONTACT4, VANO_CONTACT5, VANO_CONTACT6), service.getAll(VANO_ID));
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
        service.update(updated, VANO_ID);
        MATCHER.assertEquals(updated, service.get(VANO_CONTACT_ID, VANO_ID));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + SERG_CONTACT_ID);
        service.update(SERG_CONTACT1, VANO_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(CONTACTS, service.getAll(VANO_ID));
    }

    @Test
    public void testGetFiltered() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(VANO_CONTACT1, VANO_CONTACT3),
                service.getFiltered("as", VANO_ID));
    }
}