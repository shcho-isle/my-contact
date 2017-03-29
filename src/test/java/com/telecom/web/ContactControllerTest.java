package com.telecom.web;

import org.junit.Test;

import static com.telecom.ContactTestData.*;
import static com.telecom.TestUtil.userAuth;
import static com.telecom.UserTestData.SERG;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ContactControllerTest extends AbstractControllerTest {

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/update-" + SERG_CONTACT_ID + "-contact")
                .with(userAuth(SERG)))
//                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("details"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/details.jsp"));
    }
}