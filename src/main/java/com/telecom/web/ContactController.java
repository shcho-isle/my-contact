package com.telecom.web;

import com.telecom.model.Contact;
import com.telecom.service.ContactService;
import com.telecom.AuthorizedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ContactController {

    private final ContactService service;

    @Autowired
    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping("/delete-{id}-contact")
    public String delete(@PathVariable int id) {
        int userId = AuthorizedUser.id();
        service.delete(id, userId);
        return "redirect:contacts?message=contact.deleted";
    }

    @RequestMapping("search")
    public String search(ModelMap model, @RequestParam("searchLine") String searchLine) {
        Integer userId = AuthorizedUser.id();
        List<Contact> contactsList = service.getFiltered(searchLine, userId);
        model.addAttribute("contactList", contactsList);
        model.addAttribute("searchLine", searchLine);
        return "contacts";
    }

    @GetMapping("/contacts")
    public String getAll(ModelMap model) {
        Integer userId = AuthorizedUser.id();
        List<Contact> contacts = service.getAll(userId);
        model.addAttribute("contactList", contacts);
        return "contacts";
    }

    @GetMapping("/new")
    public String newContact(ModelMap model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("isNew", true);
        return "details";
    }

    @PostMapping("/new")
    public String saveNew(@Valid Contact contact, BindingResult result, SessionStatus status, ModelMap model) {
        if (!result.hasErrors()) {
            try {
                service.save(contact, AuthorizedUser.id());
                status.setComplete();
                return "redirect:contacts?message=contact.created&lastname=" + contact.getLastName();
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("mobilePhone", "exception.contacts.duplicate_mobilephone");
            }
        }
        model.addAttribute("isNew", true);
        return "details";
    }

    @GetMapping("/update-{id}-contact")
    public String details(@PathVariable Integer id, ModelMap model) {
        Contact contact = service.get(id, AuthorizedUser.id());
        model.addAttribute("contact", contact);
        return "details";
    }

    @PostMapping("/update-{id}-contact")
    public String updateDetails(@Valid Contact contact, BindingResult result, SessionStatus status, @PathVariable Integer id) {
        if (!result.hasErrors()) {
            try {
                service.update(contact, AuthorizedUser.id());
                status.setComplete();
                return "redirect:contacts?message=contact.updated&lastname=" + contact.getLastName();
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("mobilePhone", "exception.contacts.duplicate_mobilephone");
            }
        }
        return "details";
    }
}