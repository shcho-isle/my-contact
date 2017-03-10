package com.lardi.web;

import com.lardi.model.Contact;
import com.lardi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class ContactController {

//    @Qualifier(value = "contactJsonService") //Раскоментировать для использования JSON File
    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView getContacts(@RequestParam Map<String, String> allRequestParams) throws Exception {
        ModelAndView model = null;
        if (allRequestParams.containsKey("searchAction")) {
            String action = allRequestParams.get("searchAction");
            switch (action) {
                case "searchById":
                    model = searchContactById(allRequestParams);
                    break;
                case "searchByName":
                    model = searchContactByName(allRequestParams);
                    break;
            }
            return model;
        } else {
            List<Contact> contactList = contactService.getAllContacts();
            model = new ModelAndView("list-contacts");
            model.addObject("contactList", contactList);
            model.addObject("currentUser", SecurityContextHolder.getContext().getAuthentication().getName().toUpperCase());
            return model;
        }
    }

    @RequestMapping(value = "/new-contact", method = RequestMethod.GET)
    public ModelAndView getContacts() throws Exception {
        return new ModelAndView("new-contact");
    }

    private ModelAndView searchContactByName(Map<String, String> allRequestParams) throws IOException {
        String contactName = allRequestParams.get("contactName");
        List<Contact> result = contactService.searchContactsByFirstName(contactName);
        return forwardListContacts(result);
    }

    private ModelAndView forwardListContacts(List<Contact> result) {
        ModelAndView model = new ModelAndView("list-contacts");
        return model.addObject("contactList", result);
    }


    private ModelAndView searchContactById(Map<String, String> allRequestParams) throws Exception {
        Integer idContact = Integer.valueOf(allRequestParams.get("idContact"));
        Contact contact = contactService.getContact(idContact);
        ModelAndView model = new ModelAndView("new-contact");
        model.addObject("contact", contact);
        model.addObject("action", "edit");
        return model;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
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
            case "remove":
                model = removeContactByName(allRequestParams);
                break;
        }
        return model;
    }

    private ModelAndView addContactAction(Map<String, String> allRequestParams) throws IOException {
        String error = contactService.validateNewContact(allRequestParams);
        if (error.length() > 0) {
            ModelAndView modelAndView = new ModelAndView("new-contact");
            modelAndView.addObject("error", error);
            return modelAndView;
        }
        ModelAndView model = new ModelAndView("list-contacts");
        String name = allRequestParams.get("firstName");
        String lastName = allRequestParams.get("lastName");
        String middleName = allRequestParams.get("middleName");
        String address = allRequestParams.get("address");
        String mobilePhone = allRequestParams.get("mobilePhone");
        String homePhone = allRequestParams.get("homePhone");
        String email = allRequestParams.get("email");
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Contact contact = new Contact(lastName, name, middleName, mobilePhone, homePhone, address, email, currentUser);
        Integer idContact = contactService.saveContact(contact);
        List<Contact> contactList = contactService.getAllContacts();
        model.addObject("idContact", idContact);
        String message = "Новый контакт успешно создан";
        model.addObject("message", message);
        model.addObject("contactList", contactList);
        return model;
    }

    private ModelAndView editContactAction(Map<String, String> allRequestParams) throws Exception {
        String error = contactService.validateNewContact(allRequestParams);
        if (error.length() > 0) {
            Integer idContact = Integer.valueOf(allRequestParams.get("idContact"));
            Contact contact = contactService.getContact(idContact);
            ModelAndView model = new ModelAndView("new-contact");
            model.addObject("contact", contact);
            model.addObject("action", "edit");
            model.addObject("error", error);
            return model;
        }
        ModelAndView model = new ModelAndView("list-contacts");
        String name = allRequestParams.get("firstName");
        String lastName = allRequestParams.get("lastName");
        String middleName = allRequestParams.get("middleName");
        String address = allRequestParams.get("address");
        String mobilePhone = allRequestParams.get("mobilePhone");
        String homePhone = allRequestParams.get("homePhone");
        String email = allRequestParams.get("email");
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer idContact = Integer.valueOf(allRequestParams.get("idContact"));
        Contact contact = new Contact(idContact, lastName, name, middleName, mobilePhone, homePhone, address, email, currentUser);
        contact.setId(idContact);
        boolean success = contactService.updateContact(contact);
        String message = null;
        if (success) {
            message = "Контакт успешно обновлён!";
        }
        List<Contact> contactList = contactService.getAllContacts();
        model.addObject("idContact", idContact);
        model.addObject("message", message);
        model.addObject("contactList", contactList);
        return model;
    }

    private ModelAndView removeContactByName(Map<String, String> allRequestParams) throws IOException {
        ModelAndView model = new ModelAndView("list-contacts");
        Integer idContact = Integer.valueOf(allRequestParams.get("idContact"));
        boolean confirm = contactService.deleteContact(idContact);
        if (confirm) {
            String message = "Контакт успешно удалён!";
            model.addObject("message", message);
        }
        List<Contact> contactList = contactService.getAllContacts();
        model.addObject("contactList", contactList);
        return model;
    }
}