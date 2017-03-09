package com.lardi.web;

import com.lardi.model.User;
import com.lardi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(value = "/registr")
public class UserController {

//    @Qualifier(value = "userJsonService") //Раскоментировать для использования JSON File
    @Autowired
    private UserService userService;

    @GetMapping
    public String viewRegistration(Map<String, Object> model) {
        User userForm = new User();
        model.put("userForm", userForm);
        return "registr";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("userForm") User user,
                                      Map<String, Object> model) throws IOException {
        try {
            userService.createUser(user).getId();
        } catch (NullPointerException e) {
            model.put("message", "Пользователь с такими данными уже есть");
            return "registr";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "registrationsuccess";
    }
}