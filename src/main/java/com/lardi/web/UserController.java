package com.lardi.web;

import com.lardi.model.User;
import com.lardi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/register")
    public String viewRegistration(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("register", true);
        return "register";
    }

    @PostMapping(value = "/register")
    public String processRegistration(@Valid User user, BindingResult result, SessionStatus status, ModelMap model) {
        if (!result.hasErrors()) {
            try {
                userService.save(user);
                status.setComplete();
                return "redirect:login?message=app.registered&fullname=" + user.getFullName();
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("login", "exception.users.duplicate_login");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("register", true);
        return "register";
    }
}