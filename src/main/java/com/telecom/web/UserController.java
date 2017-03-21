package com.telecom.web;

import com.telecom.model.User;
import com.telecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid User user, BindingResult result, SessionStatus status, ModelMap model) {
        if (!result.hasErrors()) {
            try {
                userService.save(user);
                status.setComplete();
                return "redirect:login?message=login.registered&fullname=" + user.getFullName();
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("login", "exception.users.duplicate_login");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "register";
    }
}