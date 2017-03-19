package com.lardi;

import com.lardi.matcher.ModelMatcher;
import com.lardi.model.Contact;

import java.util.Arrays;
import java.util.List;


public class ContactTestData {

    public static final ModelMatcher<Contact> MATCHER = ModelMatcher.of();

    public static final int VANO_CONTACT_ID = 1;
    public static final int SERG_CONTACT_ID = VANO_CONTACT_ID + 6;

    public static final Contact VANO_CONTACT1 = new Contact(VANO_CONTACT_ID, "Dovbash", "Sveta", "Andriivna", "+380(66)1234567", "+380(44)1234567", "Kyiv, Mechnikova str. 2", "sveta@gmail.com");
    public static final Contact VANO_CONTACT2 = new Contact(VANO_CONTACT_ID + 1, "Kushnir", "Lena", "Viktorivna", "+380(50)1234123", "+380(47)2661181", "Cherkasy, Taraskova str. 16", "lena@gmail.com");
    public static final Contact VANO_CONTACT3 = new Contact(VANO_CONTACT_ID + 2, "Parasiuk", "Sasha", "Igorevych", "+380(97)9876543", "+380(44)0909098", "Kyiv, Pushkinska str. 10", "sasha@gmail.com");
    public static final Contact VANO_CONTACT4 = new Contact(VANO_CONTACT_ID + 3, "Tiagnybok", "Yulia", "Volodymyrivna", "+380(67)8555855", "+380(47)2234567", "Cherkasy, Rustavi str. 8", "yulia@gmail.com");
    public static final Contact VANO_CONTACT5 = new Contact(VANO_CONTACT_ID + 4, "Tsymbal", "Vitia", "Fedorovych", "+380(98)9234567", "+380(44)9234567", "Kyiv, Sosninykh str. 38", "vitia@gmail.com");
    public static final Contact VANO_CONTACT6 = new Contact(VANO_CONTACT_ID + 5, "Tymoshenko", "Grysha", "Petrovych", "+380(93)1234555", "+380(47)2234555", "Cherkasy, Gaidara str. 8", "grysha@gmail.com");
    public static final Contact SERG_CONTACT1 = new Contact(SERG_CONTACT_ID, "Kukushkina", "Dima", "Grygorovych", "+380(63)3334567", "+380(44)3334567", "Kyiv, Zhylanska str. 99", "dima@gmail.com");
    public static final Contact SERG_CONTACT2 = new Contact(SERG_CONTACT_ID + 1, "Kukushkina", "Sveta", "Andreevna", "+380(66)1234567", "+380(44)1234567", "Kyiv, Mechnikova str. 2", "sveta@gmail.com");

    public static final List<Contact> CONTACTS = Arrays.asList(VANO_CONTACT1, VANO_CONTACT2, VANO_CONTACT3, VANO_CONTACT4, VANO_CONTACT5, VANO_CONTACT6);

    public static Contact getCreated() {
        return new Contact(null, "A new_last", "new_first", "new_middle", "+380(00)0000000", "", "", "");
    }

    public static Contact getUpdated() {
        return new Contact(VANO_CONTACT_ID, "updated_last", "updated_first", "updated_middle", VANO_CONTACT1.getMobilePhone(), VANO_CONTACT1.getHomePhone(), "updated_address", "updated@gmail.com");
    }
}
