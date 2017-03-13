package com.lardi.web;

import com.lardi.service.ContactService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.lardi.TestUtil.userAuth;
import static com.lardi.TestUtil.userHttpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.lardi.UserTestData.*;
import static com.lardi.ContactTestData.*;
import static com.lardi.ContactTestData.MATCHER;

public class ContactControllerTest extends AbstractControllerTest {

    @Autowired
    private ContactService service;

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(get("/delete-" + VANO_CONTACT_ID + "-contact")
                .with(userAuth(VANO)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("contacts"));
        MATCHER.assertCollectionEquals(Arrays.asList(VANO_CONTACT2, VANO_CONTACT3, VANO_CONTACT4, VANO_CONTACT5, VANO_CONTACT6), service.getAll(VANO_ID));
    }
}
