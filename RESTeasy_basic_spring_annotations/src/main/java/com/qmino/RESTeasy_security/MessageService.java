
package com.qmino.RESTeasy_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Service
@Path("/message")
public class MessageService {

    @GET
    @Path("/secured")
    @Produces("text/plain")
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public String getMessageSecured(@Context HttpServletRequest request) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) a.getPrincipal();

        if (request.isUserInRole("ROLE_ADMIN")) {
            return "Hello, admin " + userDetails.getUsername() + "!";
        } else {
            return "Hello, user " + userDetails.getUsername() + "!";
        }
    }

    @GET
    @Path("/rolesAllowed")
    @Produces("text/plain")
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public String getMessageRolesAllowed(@Context HttpServletRequest request) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) a.getPrincipal();

        if (request.isUserInRole("ROLE_ADMIN")) {
            return "Hello, admin " + userDetails.getUsername() + "!";
        } else {
            return "Hello, user " + userDetails.getUsername() + "!";
        }
    }

    @GET
    @Path("/permitAll")
    @Produces("text/plain")
    @DenyAll // <- doesn't work
    public String permitAll() {
        return "Hello!";
    }
}