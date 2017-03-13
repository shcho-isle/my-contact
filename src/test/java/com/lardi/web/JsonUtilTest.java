package com.lardi.web;

import com.lardi.model.Contact;
import com.lardi.web.json.JsonUtil;
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
        List<Contact> contact = JsonUtil.readValues(json, Contact.class);
        ContactTestData.MATCHER.assertCollectionEquals(ContactTestData.CONTACTS, contact);
    }
}