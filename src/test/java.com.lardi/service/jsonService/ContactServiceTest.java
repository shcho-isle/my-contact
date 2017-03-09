package service.jsonService;

import com.lardi.model.Contact;
import com.lardi.service.ContactService;
import com.lardi.service.ServiceUtils;
import com.lardi.service.jsonService.ContactServiceImpl;
import com.lardi.service.jsonService.JsonFileServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(classes = JsonFileServiceImpl.class),
        @ContextConfiguration(classes = ContactServiceImpl.class)
})
@WithMockUser(username = "max")
public class ContactServiceTest {

    @Autowired
    private ContactService contactJsonService;

    @Before
    public void createTestContact() throws IOException {
        Properties properties = ServiceUtils.getProperties();
        new File(properties.getProperty("pathToFileFolder") + Contact.class.getName() + ".json").delete();
        contactJsonService.saveContact(new Contact("TestLastName", "TestName", "TestMiddleName", "+380(96)1234567", "234234234", "New York", "test@test.cmo", "max"));
    }

    @Test
    public void shouldAddContact() throws IOException {
        Contact contact = new Contact("Zavodnyuk", "Maxim", "Alexandrovich", "+380(96)1006940", "234234234", "152A", "ultimax@ukr.net", "max");
        contactJsonService.saveContact(contact);
        Contact contact2 = new Contact("Krugliak", "Kristina", "Alexandrovna", "54321", "4325435", "17", "mysyundra@yandex.ru", "max");
        contactJsonService.saveContact(contact2);
    }

    @Test
    public void shouldGetAllContacts() throws IOException {
        assertTrue(contactJsonService.getAllContacts().size() > 0);
    }

    @Test
    public void shouldUpdateContact() throws IOException {
        Contact contact = new Contact(101, "Zavodnyuk", "Maxim", "Alexandrovich", "+380(96)1006940", "234234234", "152A", "ultimax@ukr.net", "max");
        contactJsonService.updateContact(contact);
    }

    @Test
    public void shouldDeteleteContact() throws IOException {
        contactJsonService.deleteContact(103);
    }

    @Test
    public void shouldGetContact() throws Exception {
        Contact contact = new Contact("TestLastName", "TestName", "TestMiddleName", "+380(96)1234567", "234234234", "New York", "test@test.cmo", "max");
        Contact returnedContact = contactJsonService.getContact(101);
        assertEquals(contact.getHomePhone(), returnedContact.getHomePhone());
    }

    @Test
    public void shouldSearchContactByName() throws IOException {
        List<Contact> returnedList = contactJsonService.searchContactsByFirstName("TestName");
        assertTrue(returnedList.size() > 0);
    }

    @Test
    public void shouldValidateNewContact() {
        Map<String, String> allRequestParams = new HashMap<>();
        allRequestParams.put("firstName", "ma");
        allRequestParams.put("lastName", "fgfdgsdf");
        allRequestParams.put("middleName", "maxfdgdfg");
        allRequestParams.put("address", "maxsdf");
        allRequestParams.put("mobilePhone", "+38(096)1006940");
        allRequestParams.put("homePhone", "2423");
        allRequestParams.put("email", "alex@1.com");
        System.out.println(contactJsonService.validateNewContact(allRequestParams));
    }
}
