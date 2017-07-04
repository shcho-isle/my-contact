package com.telecom.web;

import com.telecom.model.Contact;
import com.telecom.service.ContactService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.telecom.UserTestData.*;
import static com.telecom.ContactTestData.*;
import static com.telecom.ContactTestData.MATCHER;
import static com.telecom.TestUtil.userAuth;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

public class ContactControllerTest extends AbstractControllerTest {

    @Autowired
    private ContactService service;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/update-" + USERY_CONTACT_ID + "-contact")
                .with(userAuth(USERY)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("details"))
                .andExpect(model().attribute("contact", is(USERY_CONTACT1)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get("/update-" + USERY_CONTACT_ID + "-contact"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/"));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get("/update-" + USERY_CONTACT_ID + "-contact")
                .with(userAuth(USERX)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("exception/exception"));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(get("/delete-" + USERY_CONTACT_ID + "-contact")
                .with(userAuth(USERX)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("exception/exception"));
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(get("/delete-" + USERX_CONTACT_ID + "-contact")
                .with(userAuth(USERX)))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("contacts?message=contacts.deleted"));
        MATCHER.assertCollectionEquals(Arrays.asList(USERX_CONTACT2, USERX_CONTACT3, USERX_CONTACT4, USERX_CONTACT5, USERX_CONTACT6), service.getAll(USERX_ID));
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Contact updated = getUpdated();

        mockMvc.perform(post("/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", updated.getId().toString())
                .param("lastName", updated.getLastName())
                .param("firstName", updated.getFirstName())
                .param("middleName", updated.getMiddleName())
                .param("mobilePhone", updated.getMobilePhone())
                .with(userAuth(USERX))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("contacts?message=contacts.updated"));

        MATCHER.assertEquals(updated, service.get(USERX_CONTACT_ID, USERX_ID));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        mockMvc.perform(post("/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", USERX_CONTACT_ID.toString())
                .with(userAuth(USERX))
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("details"));

        MATCHER.assertEquals(USERX_CONTACT1, service.get(USERX_CONTACT_ID, USERX_ID));
    }

    @Test
    public void testUpdateHtmlUnsafe() throws Exception {
        mockMvc.perform(post("/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", USERX_CONTACT_ID.toString())
                .param("lastName", "LastName")
                .param("firstName", "FirstName")
                .param("middleName", "<script>alert(123)</script>")
                .param("mobilePhone", "+380(66)1234567")
                .with(userAuth(USERX))
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("details"));

        MATCHER.assertEquals(USERX_CONTACT1, service.get(USERX_CONTACT_ID, USERX_ID));
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        Contact created = getCreated();
        mockMvc.perform(post("/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("lastName", created.getLastName())
                .param("firstName", created.getFirstName())
                .param("middleName", created.getMiddleName())
                .param("mobilePhone", created.getMobilePhone())
                .param("homePhone", "")
                .param("address", "")
                .param("email", "")
                .with(userAuth(USERY))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("contacts?message=contacts.created"));
        assertEquals(3, service.getAll(USERY_ID).size());
    }

    @Test
    public void testCreateInvalid() throws Exception {
        mockMvc.perform(post("/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("firstName", "Dummy")
                .with(userAuth(USERY))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("details"))
                .andExpect(model().attribute("isNew", is(true)));
        MATCHER.assertCollectionEquals(Arrays.asList(USERY_CONTACT1, USERY_CONTACT2), service.getAll(USERY_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/contacts")
                .with(userAuth(USERY)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute("contactList", is(Arrays.asList(USERY_CONTACT1, USERY_CONTACT2))));
    }

    @Test
    public void testSearch() throws Exception {
        mockMvc.perform(get("/search?searchLine=55")
                .with(userAuth(USERX)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute("contactList", is(Arrays.asList(USERX_CONTACT4, USERX_CONTACT6))));
    }

    @Test
    public void testUpdateDuplicate() throws Exception {
        Contact invalid = new Contact(USERX_CONTACT_ID, "Dummy", "Dummy", "Dummy", USERX_CONTACT2.getMobilePhone());
        mockMvc.perform(post("/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", invalid.getId().toString())
                .param("lastName", invalid.getLastName())
                .param("firstName", invalid.getFirstName())
                .param("middleName", invalid.getMiddleName())
                .param("mobilePhone", invalid.getMobilePhone())
                .with(userAuth(USERX))
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(view().name("details"));
        MATCHER.assertEquals(USERX_CONTACT1, service.get(USERX_CONTACT_ID, USERX_ID));
    }

    @Test
    public void testCreateDuplicate() throws Exception {
        Contact invalid = new Contact(null, "Dummy", "Dummy", "Dummy", USERY_CONTACT1.getMobilePhone());
        mockMvc.perform(post("/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("lastName", invalid.getLastName())
                .param("firstName", invalid.getFirstName())
                .param("middleName", invalid.getMiddleName())
                .param("mobilePhone", invalid.getMobilePhone())
                .param("homePhone", "")
                .param("address", "")
                .param("email", "")
                .with(userAuth(USERY))
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(view().name("details"));
        MATCHER.assertCollectionEquals(Arrays.asList(USERY_CONTACT1, USERY_CONTACT2), service.getAll(USERY_ID));
    }
}