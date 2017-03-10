package com.lardi.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceUtils {
    public static final Properties PROPERTIES;

    static {
        FileInputStream fis;
        PROPERTIES = new Properties();

        String config = System.getProperty("lardi.conf");

        try {
            if (config == null) {
                fis = new FileInputStream("config/storage.properties");
            } else {
                fis = new FileInputStream(System.getProperty("lardi.conf"));
            }
            PROPERTIES.load(fis);
        } catch (IOException e) {
            System.err.println("ERROR: Properties file is absent!");
        }
    }

    public static String validateNewContact(Map<String, String> allRequestParams) {
        String dot = "&#9658; ";
        String message = "";
        if (allRequestParams.get("firstName").length() < 4) {
            message += dot + "Имя слишком короткое\n";
        }
        if (allRequestParams.get("lastName").length() < 4) {
            message += dot + "Фамилия слишком короткая\n";
        }
        if (allRequestParams.get("middleName").length() < 4) {
            message += dot + "Отчество слишком короткое\n";
        }
        String mobilePhone = allRequestParams.get("mobilePhone");
        Pattern phonePattern = Pattern.compile("^(\\+)380[(]\\d{2}[)][0-9]{7}$");
        Matcher phoneMatcher = phonePattern.matcher(mobilePhone);
        if (!phoneMatcher.matches()) {
            message += dot + "Неверный формат номера телефона\n";
        }
        String email = allRequestParams.get("email");
        if (email.length() > 0) {
            Pattern emailPattern = Pattern.compile("^\\w+@[0-9a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
            Matcher emailMatcher = emailPattern.matcher(email);
            if (!emailMatcher.matches()) {
                message += dot + "Неверный email";
            }
        }
        return message;
    }

    public static Properties getProperties() {
        return PROPERTIES;
    }
}
