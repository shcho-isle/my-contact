package com.telecom.web;

import com.telecom.model.Contact;
import com.telecom.service.ContactService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.telecom.UserTestData.*;
import static org.junit.Assert.assertEquals;
import static com.telecom.ContactTestData.*;
import static com.telecom.ContactTestData.MATCHER;
import static com.telecom.TestUtil.userAuth;
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
        mockMvc.perform(get("/update-" + SERG_CONTACT_ID + "-contact")
                .with(userAuth(SERG)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("details"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/details.jsp"))
                .andExpect(model().attribute("contact", is(SERG_CONTACT1)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get("/update-" + SERG_CONTACT_ID + "-contact"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/"));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get("/update-" + SERG_CONTACT_ID + "-contact")
                .with(userAuth(VANO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("exception/exception"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/exception/exception.jsp"));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(get("/delete-" + SERG_CONTACT_ID + "-contact")
                .with(userAuth(VANO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("exception/exception"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/exception/exception.jsp"));
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(get("/delete-" + VANO_CONTACT_ID + "-contact")
                .with(userAuth(VANO)))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("contacts?message=contact.deleted"));
        MATCHER.assertCollectionEquals(Arrays.asList(VANO_CONTACT2, VANO_CONTACT3, VANO_CONTACT4, VANO_CONTACT5, VANO_CONTACT6), service.getAll(VANO_ID));
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
                .with(userAuth(VANO))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("contacts?message=contact.updated"));

        assertEquals(updated, service.get(VANO_CONTACT_ID, VANO_ID));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        mockMvc.perform(post("/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", VANO_CONTACT_ID.toString())
                .with(userAuth(VANO))
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("details"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/details.jsp"));

        assertEquals(VANO_CONTACT1, service.get(VANO_CONTACT_ID, VANO_ID));
    }

    @Test
    public void testUpdateHtmlUnsafe() throws Exception {
        mockMvc.perform(post("/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", VANO_CONTACT_ID.toString())
                .param("lastName", "LastName")
                .param("firstName", "FirstName")
                .param("middleName", "<script>alert(123)</script>")
                .param("mobilePhone", "+380(66)1234567")
                .with(userAuth(VANO))
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("details"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/details.jsp"));

        assertEquals(VANO_CONTACT1, service.get(VANO_CONTACT_ID, VANO_ID));
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
                .with(userAuth(SERG))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("contacts?message=contact.created"));

        created.setId(9);
        MATCHER.assertCollectionEquals(Arrays.asList(created, SERG_CONTACT1, SERG_CONTACT2), service.getAll(SERG_ID));
    }

    @Test
    public void testCreateInvalid() throws Exception {
        Contact invalid = new Contact(null, null, "Dummy", null, null);

        mockMvc.perform(post("/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("firstName", "Dummy")
                .with(userAuth(SERG))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("details"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/details.jsp"))
                .andExpect(model().attribute("isNew", is(true)));

        MATCHER.assertCollectionEquals(Arrays.asList(SERG_CONTACT1, SERG_CONTACT2), service.getAll(SERG_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/contacts")
                .with(userAuth(SERG)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute("contactList", is(Arrays.asList(SERG_CONTACT1, SERG_CONTACT2))));
    }
//
//    @Test
//    public void testGetBetween() throws Exception {
//        mockMvc.perform(get("search?searchLine=55")
//                .with(userAuth(VANO)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(model().attribute("contactList", is(Arrays.asList(VANO_CONTACT4, VANO_CONTACT6))));
//    }

//    @Test
//    public void testFilter() throws Exception {
//        mockMvc.perform(get(REST_URL + "filter")
//                .param("startDate", "2015-05-30").param("startTime", "07:00")
//                .param("endDate", "2015-05-31").param("endTime", "11:00")
//                .with(userHttpBasic(USER)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(
//                        MealsUtil.createWithExceed(MEAL4, true),
//                        MealsUtil.createWithExceed(MEAL1, false)));
//    }
//
//    @Test
//    public void testFilterAll() throws Exception {
//        mockMvc.perform(get(REST_URL + "filter?startDate=&endTime=")
//                .with(userHttpBasic(USER)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(
//                        MealsUtil.getWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), USER.getCaloriesPerDay())));
//    }
//
//    @Test
//    public void testUpdateDuplicate() throws Exception {
//        Meal invalid = new Meal(MEAL1_ID, MEAL2.getDateTime(), "Dummy", 200);
//        mockMvc.perform(put(REST_URL + MEAL1_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(invalid))
//                .with(userHttpBasic(USER)))
//                .andDo(print())
//                .andExpect(status().isConflict());
//    }
//
//    @Test
//    public void testCreateDuplicate() throws Exception {
//        Meal invalid = new Meal(null, ADMIN_MEAL1.getDateTime(), "Dummy", 200);
//        mockMvc.perform(post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(invalid))
//                .with(userHttpBasic(ADMIN)))
//                .andDo(print())
//                .andExpect(status().isConflict());
//    }
}