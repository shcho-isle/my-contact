package com.lardi.web;

import org.junit.Test;

import static com.lardi.TestUtil.userAuth;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.lardi.UserTestData.VANO;

public class ContactControllerTest extends AbstractControllerTest {

    @Test
    public void testMeals() throws Exception {
        mockMvc.perform(get("/contact")
                .with(userAuth(VANO)))
                .andDo(print())
                .andExpect(view().name("list-contacts"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/list-contacts.jsp"));
    }
}