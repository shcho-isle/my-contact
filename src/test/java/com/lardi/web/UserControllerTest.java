package com.lardi.web;

import org.junit.Test;

import static com.lardi.TestUtil.userAuth;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.lardi.UserTestData.VANO;

public class UserControllerTest extends AbstractControllerTest {
    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(view().name("login"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/login.jsp"));
    }

    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(get("/register"))
                .andDo(print())
                .andExpect(view().name("register"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/register.jsp"));
    }
}