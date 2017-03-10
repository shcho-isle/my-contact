package com.lardi.web;

import com.lardi.model.User;
import com.lardi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

//    @Qualifier(value = "userJsonService") //Раскоментировать для использования JSON File
    @Autowired
    private UserService userService;

    @GetMapping
    public String viewRegistration(ModelMap model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String processRegistration(@Valid User user, BindingResult result, SessionStatus status) {
        if (!result.hasErrors()) {
            try {
                userService.createUser(user);
                status.setComplete();
                return "redirect:login?message=app.registered&fullname=" + user.getFullName();
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("email", "exception.users.duplicate_email");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "register";
    }
}