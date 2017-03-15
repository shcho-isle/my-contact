package com.lardi.util;

import com.lardi.util.exception.NotFoundException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static String validateNewContact(Map<String, String> allRequestParams) {
        String dot = "&#9658; ";
        String message = "";
        if (allRequestParams.get("firstName").length() < 4) {
            message += dot + "First Name is too short\n";
        }
        if (allRequestParams.get("lastName").length() < 4) {
            message += dot + "Last Name is too short\n";
        }
        if (allRequestParams.get("middleName").length() < 4) {
            message += dot + "Middle Name is too short\n";
        }
        String mobilePhone = allRequestParams.get("mobilePhone");
        Pattern MobilePhPattern = Pattern.compile("^(\\+)380[(]\\d{2}[)][0-9]{7}$");
        Matcher mobilePhMatcher = MobilePhPattern.matcher(mobilePhone);
        if (!mobilePhMatcher.matches()) {
            message += dot + "Wrong Mobile Phone format\n";
        }
        String homePhone = allRequestParams.get("homePhone");
        Pattern homePhPattern = Pattern.compile("(^(\\+)380[(]\\d{2}[)][0-9]{7}$)?");
        Matcher homePhMatcher = homePhPattern.matcher(homePhone);
        if (!homePhMatcher.matches()) {
            message += dot + "Wrong Home Phone format\n";
        }
        String email = allRequestParams.get("email");
        if (email.length() > 0) {
            Pattern emailPattern = Pattern.compile("^\\w+@[0-9a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
            Matcher emailMatcher = emailPattern.matcher(email);
            if (!emailMatcher.matches()) {
                message += dot + "Wrong email";
            }
        }
        return message;
    }
}
