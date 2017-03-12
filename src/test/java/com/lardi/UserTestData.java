package com.lardi;

import com.lardi.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lardi.matcher.ModelMatcher;
import com.lardi.util.PasswordUtil;

import java.util.Objects;

public class UserTestData {
    private static final Logger LOG = LoggerFactory.getLogger(UserTestData.class);

    public static final int USER1_ID = 1;
    public static final int USER2_ID = USER1_ID + 1;

    public static final User USER1 = new User(USER1_ID, "Vano", "password", "Yakovenko Ivan Venediktovich");
    public static final User USER2 = new User(USER2_ID, "Serg", "password", "Rubinov Sergey Nikolaevich");

    public static final ModelMatcher<User> MATCHER = ModelMatcher.of(User.class,
            (expected, actual) -> expected == actual ||
                    (comparePassword(expected.getPassword(), actual.getPassword())
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