package com.telecom;

import com.telecom.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.telecom.matcher.ModelMatcher;
import com.telecom.util.PasswordUtil;

import java.util.Objects;

public class UserTestData {
    private static final Logger LOG = LoggerFactory.getLogger(UserTestData.class);

    public static final Integer USERX_ID = 1;
    public static final Integer USERY_ID = USERX_ID + 1;

    public static final User USERX = new User(USERX_ID, "USERX", "password", "Yakovenko Ivan Venediktovich");
    public static final User USERY = new User(USERY_ID, "USERY", "password", "Rubinov Sergey Nikolaevich");

    public static final ModelMatcher<User> MATCHER = ModelMatcher.of(
            (expected, actual) -> expected == actual || (
                    comparePassword(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getFullName(), actual.getFullName())
                            && Objects.equals(expected.getLogin(), actual.getLogin())
                    )
    );

    private static boolean comparePassword(String rawOrEncodedPassword, String password) {
        if (PasswordUtil.isEncoded(rawOrEncodedPassword)) {
            return rawOrEncodedPassword.equals(password);
        } else if (!PasswordUtil.isMatch(rawOrEncodedPassword, password)) {
            LOG.error("Password " + password + " doesn't match encoded " + password);
            return false;
        }
        return true;
    }

}