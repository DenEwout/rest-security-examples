
package com.qmino.RESTeasy_security;

import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

public class MySecurityContext implements javax.ws.rs.core.SecurityContext {

    private final User user;

    public MySecurityContext(User user) {
        this.user = user;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public boolean isUserInRole(String role) {
        return user.hasRole(role);
    }
}