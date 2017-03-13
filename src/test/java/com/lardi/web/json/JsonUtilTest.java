package com.lardi.web.json;

import com.lardi.model.Contact;
import org.junit.Test;
import com.lardi.ContactTestData;

import java.util.List;

public class JsonUtilTest {

    @Test
    public void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(ContactTestData.SERG_CONTACT1);
        System.out.println(json);
        Contact contact = JsonUtil.readValue(json, Contact.class);
        ContactTestData.MATCHER.assertEquals(ContactTestData.SERG_CONTACT1, contact);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(ContactTestData.CONTACTS);
        System.out.println(json);
        List<Contact> meals = JsonUtil.readValues(json, Contact.class);
        ContactTestData.MATCHER.assertCollectionEquals(ContactTestData.CONTACTS, meals);
    }
}