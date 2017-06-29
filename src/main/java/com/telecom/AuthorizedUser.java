package com.telecom;

import com.telecom.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.telecom.model.User;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private User user;

    public AuthorizedUser(User user, List<Role> roles) {
        super(user.getLogin(), user.getPassword(), true, true, true, true, roles);
        this.user = user;
    }

    private static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static int id() {
        AuthorizedUser authorizedUser = safeGet();
        requireNonNull(authorizedUser, "No authorized user found");
        return authorizedUser.user.getId();
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
