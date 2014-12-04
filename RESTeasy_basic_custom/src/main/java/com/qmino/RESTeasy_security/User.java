package com.qmino.RESTeasy_security;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerrit on 02.12.14.
 */
public class User implements Principal {

    private String name;
    private List<String> roles;

    public User(String name, List<String> roles) {
        this.name = name;
        this.roles = roles;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }
}
