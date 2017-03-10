package com.lardi.config;

import com.lardi.model.User;
import com.lardi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "userJsonDetailsService")
class CustomUserDetails implements UserDetailsService {

    @Autowired
    private UserService userJsonService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
        List<GrantedAuthority> gas = new ArrayList<>();
        gas.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        User u = null;
        try {
            u = userJsonService.getByLogin(login);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (u == null) {
            throw new UsernameNotFoundException("User " + login + " is not found");
        }
        return new org.springframework.security.core.userdetails.User(u.getLogin(), u.getPassword(), gas);
    }
}