package com.telecom.web;

import com.telecom.model.Contact;
import com.telecom.service.ContactService;
import com.telecom.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static com.telecom.ContactTestData.*;
import static com.telecom.TestUtil.userAuth;
import static com.telecom.UserTestData.SERG;
import static com.telecom.UserTestData.VANO;
import static com.telecom.UserTestData.VANO_ID;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .andExpect(forwardedUrl("/WEB-INF/jsp/details.jsp"));
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
                .param("homePhone", updated.getHomePhone())
                .param("address", updated.getAddress())
                .param("email", updated.getEmail())
                .sessionAttr("contact", updated)
                .with(userAuth(VANO))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("contacts?message=contact.updated&lastname=" + updated.getLastName()));

        assertEquals(updated, service.get(VANO_CONTACT_ID, VANO_ID));
    }

//    @Test
//    public void testUpdateInvalid() throws Exception {
//        Meal invalid = new Meal(MEAL1_ID, null, null, 6000);
//        mockMvc.perform(put(REST_URL + MEAL1_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(invalid))
//                .with(userHttpBasic(USER)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    public void testUpdateHtmlUnsafe() throws Exception {
//        Meal invalid = new Meal(MEAL1_ID, LocalDateTime.now(), "<script>alert(123)</script>", 200);
//        mockMvc.perform(put(REST_URL + MEAL1_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(invalid))
//                .with(userHttpBasic(USER)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    @Transactional
//    public void testCreate() throws Exception {
//        Meal created = getCreated();
//        ResultActions action = mockMvc.perform(post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(created))
//                .with(userHttpBasic(ADMIN)));
//
//        Meal returned = MATCHER.fromJsonAction(action);
//        created.setId(returned.getId());
//
//        MATCHER.assertEquals(created, returned);
//        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL2, created, ADMIN_MEAL1), service.getAll(ADMIN_ID));
//    }
//
//    @Test
//    public void testCreateInvalid() throws Exception {
//        Meal invalid = new Meal(null, null, "Dummy", 200);
//        mockMvc.perform(post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(invalid))
//                .with(userHttpBasic(ADMIN)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    public void testGetAll() throws Exception {
//        mockMvc.perform(get(REST_URL)
//                .with(userHttpBasic(USER)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(MealsUtil.getWithExceeded(MEALS, USER.getCaloriesPerDay())));
//    }
//
//    @Test
//    public void testGetBetween() throws Exception {
//        mockMvc.perform(get(REST_URL + "between?startDateTime=2015-05-30T07:00&endDateTime=2015-05-31T11:00:00")
//                .with(userHttpBasic(USER)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(
//                        MealsUtil.createWithExceed(MEAL4, true),
//                        MealsUtil.createWithExceed(MEAL1, false)));
//    }
//
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