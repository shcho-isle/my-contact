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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class ContactController {

    @Autowired
    private ContactService service;

    @GetMapping("/delete-{id}-contact")
    public String delete(@PathVariable int id) {
        int userId = AuthorizedUser.id();
        service.delete(id, userId);
        return "redirect:contacts";
    }

    @RequestMapping("searchContact")
    public String search(ModelMap model, @RequestParam("searchRequest") String searchRequest) {
        Integer userId = AuthorizedUser.id();
        List<Contact> contactsList = service.getFiltered(searchRequest, userId);
        model.addAttribute("contactList", contactsList);
        model.addAttribute("searchRequest", searchRequest);
        return "contacts";
    }

    @GetMapping("/contacts")
    public ModelAndView getAll(@RequestParam Map<String, String> allRequestParams) throws Exception {
        ModelAndView model;
        if (allRequestParams.containsKey("searchAction")) {
            model = searchById(allRequestParams);
            return model;
        } else {
            Integer userId = AuthorizedUser.id();
            List<Contact> contactList = service.getAll(userId);
            model = new ModelAndView("contacts");
            model.addObject("contactList", contactList);
            return model;
        }
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
            } catch (Exception e) {
                e.printStackTrace();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "details";
    }

    private ModelAndView searchById(Map<String, String> allRequestParams) throws Exception {
        Integer idContact = Integer.valueOf(allRequestParams.get("idContact"));
        Integer userId = AuthorizedUser.id();
        Contact contact = service.get(idContact, userId);
        ModelAndView model = new ModelAndView("details");
        model.addObject("contact", contact);
        model.addObject("action", "edit");
        return model;
    }
}