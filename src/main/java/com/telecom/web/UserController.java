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

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("user", new User());
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid User user, BindingResult result, SessionStatus status) {
        if (!result.hasErrors()) {
            try {
                userService.save(user);
                status.setComplete();
                return "redirect:login?message=login.registered";
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("login", "exception.user.duplicate_login");
            }
        }
        return "profile";
    }
}