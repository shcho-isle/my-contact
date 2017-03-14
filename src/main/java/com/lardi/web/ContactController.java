package com.lardi.web;

import com.lardi.model.Contact;
import com.lardi.service.ContactService;
import com.lardi.AuthorizedUser;
import com.lardi.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class ContactController {

    @Autowired
    private ContactService service;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/delete-{id}-contact")
    public String deleteUser(@PathVariable int id) {
        int userId = AuthorizedUser.id();
        service.delete(id, userId);
        return "redirect:contacts";
    }

    @RequestMapping("searchContact")
    public String searchUser(ModelMap model, @RequestParam("searchRequest") String searchRequest) {
        Integer userId = AuthorizedUser.id();
        List<Contact> contactsList = service.getFiltered(searchRequest, userId);
        model.addAttribute("contactList", contactsList);
        model.addAttribute("currentUser", AuthorizedUser.getFullName());
        return "contacts";
    }

    @GetMapping(value = "/contacts")
    public ModelAndView getContacts(@RequestParam Map<String, String> allRequestParams) throws Exception {
        ModelAndView model;
        if (allRequestParams.containsKey("searchAction")) {
            model = searchContactById(allRequestParams);
            return model;
        } else {
            Integer userId = AuthorizedUser.id();
            List<Contact> contactList = service.getAll(userId);
            model = new ModelAndView("contacts");
            model.addObject("contactList", contactList);
            model.addObject("currentUser", AuthorizedUser.getFullName());
            return model;
        }
    }

    @GetMapping(value = "/new-contact")
    public ModelAndView getContacts() throws Exception {
        return new ModelAndView("new-contact");
    }

    private ModelAndView forwardListContacts(List<Contact> result) {
        ModelAndView model = new ModelAndView("contacts");
        model.addObject("currentUser", AuthorizedUser.getFullName());
        return model.addObject("contactList", result);
    }

    private ModelAndView searchContactById(Map<String, String> allRequestParams) throws Exception {
        Integer idContact = Integer.valueOf(allRequestParams.get("idContact"));
        Integer userId = AuthorizedUser.id();
        Contact contact = service.get(idContact, userId);
        ModelAndView model = new ModelAndView("new-contact");
        model.addObject("contact", contact);
        model.addObject("action", "edit");
        return model;
    }

    @PostMapping(value = "/contacts")
    public ModelAndView postContacts(@RequestParam Map<String, String> allRequestParams) throws Exception {
        ModelAndView model = null;
        String action = allRequestParams.get("action");
        switch (action) {
            case "add":
                model = addContactAction(allRequestParams);
                break;
            case "edit":
                model = editContactAction(allRequestParams);
                break;
        }
        model.addObject("currentUser", AuthorizedUser.getFullName());
        return model;
    }

    private ModelAndView addContactAction(Map<String, String> allRequestParams) throws IOException {
        String error = ValidationUtil.validateNewContact(allRequestParams);
        if (error.length() > 0) {
            ModelAndView modelAndView = new ModelAndView("new-contact");
            modelAndView.addObject("error", error);
            return modelAndView;
        }
        ModelAndView model = new ModelAndView("contacts");
        String name = allRequestParams.get("firstName");
        String lastName = allRequestParams.get("lastName");
        String middleName = allRequestParams.get("middleName");
        String address = allRequestParams.get("address");
        String mobilePhone = allRequestParams.get("mobilePhone");
        String homePhone = allRequestParams.get("homePhone");
        String email = allRequestParams.get("email");
        Contact contact = new Contact(lastName, name, middleName, mobilePhone, homePhone, address, email);
        Integer userId = AuthorizedUser.id();
        Contact savedContact = service.save(contact, userId);
        List<Contact> contactList = service.getAll(userId);
        model.addObject("idContact", savedContact.getId());
        String message = messageSource.getMessage("contact.created", new String[]{savedContact.getLastName()}, Locale.ENGLISH);
        model.addObject("message", message);
        model.addObject("contactList", contactList);
        return model;
    }

    private ModelAndView editContactAction(Map<String, String> allRequestParams) throws Exception {
        String error = ValidationUtil.validateNewContact(allRequestParams);
        if (error.length() > 0) {
            Integer idContact = Integer.valueOf(allRequestParams.get("idContact"));
            Integer userId = AuthorizedUser.id();
            Contact contact = service.get(idContact, userId);
            ModelAndView model = new ModelAndView("new-contact");
            model.addObject("contact", contact);
            model.addObject("action", "edit");
            model.addObject("error", error);
            return model;
        }
        ModelAndView model = new ModelAndView("contacts");
        String name = allRequestParams.get("firstName");
        String lastName = allRequestParams.get("lastName");
        String middleName = allRequestParams.get("middleName");
        String address = allRequestParams.get("address");
        String mobilePhone = allRequestParams.get("mobilePhone");
        String homePhone = allRequestParams.get("homePhone");
        String email = allRequestParams.get("email");
        Integer idContact = Integer.valueOf(allRequestParams.get("idContact"));
        Contact contact = new Contact(idContact, lastName, name, middleName, mobilePhone, homePhone, address, email);
        contact.setId(idContact);
        Integer userId = AuthorizedUser.id();
        service.update(contact, userId);
        String message = messageSource.getMessage("contact.updated", new String[]{contact.getLastName()}, Locale.ENGLISH);
        List<Contact> contactList = service.getAll(userId);
        model.addObject("idContact", idContact);
        model.addObject("message", message);
        model.addObject("contactList", contactList);
        return model;
    }
}