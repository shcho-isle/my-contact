package com.telecom.web;

import com.telecom.model.User;
import com.telecom.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

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
                LOG.info("register {}", user);
                status.setComplete();
                return "redirect:login?message=login.registered&username=" + user.getLogin();
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("login", "exception.user.duplicate_login");
            }
        }
        return "profile";
    }
}