package com.telecom.service;

import com.telecom.model.Contact;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.telecom.service.exception.NotFoundException;

import java.util.Arrays;

import static com.telecom.ContactTestData.*;
import static com.telecom.UserTestData.USERX_ID;

public abstract class AbstractContactServiceTest extends AbstractServiceTest {

    @Autowired
    protected ContactService service;

    @Test
    public void testDelete() throws Exception {
        service.delete(USERX_CONTACT_ID, USERX_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USERX_CONTACT2, USERX_CONTACT3, USERX_CONTACT4, USERX_CONTACT5, USERX_CONTACT6), service.getAll(USERX_ID));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(USERY_CONTACT_ID, USERX_ID);
    }

    @Test
    public void testSave() throws Exception {
        Contact created = getCreated();
        service.save(created, USERX_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(created, USERX_CONTACT1, USERX_CONTACT2, USERX_CONTACT3, USERX_CONTACT4, USERX_CONTACT5, USERX_CONTACT6), service.getAll(USERX_ID));
    }

    @Test
    public void testGet() throws Exception {
        Contact actual = service.get(USERX_CONTACT_ID, USERX_ID);
        MATCHER.assertEquals(USERX_CONTACT1, actual);
    }

    @Test
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(USERY_CONTACT_ID, USERX_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        Contact updated = getUpdated();
        service.update(updated, USERX_ID);
        MATCHER.assertEquals(updated, service.get(USERX_CONTACT_ID, USERX_ID));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + USERY_CONTACT_ID);
        service.update(USERY_CONTACT1, USERX_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(CONTACTS, service.getAll(USERX_ID));
    }

    @Test
    public void testGetFiltered() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(USERX_CONTACT1, USERX_CONTACT3),
                service.getFiltered("as", USERX_ID));
    }
}