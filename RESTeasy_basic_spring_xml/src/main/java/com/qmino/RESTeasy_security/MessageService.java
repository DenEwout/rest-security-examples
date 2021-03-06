
package com.qmino.RESTeasy_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
    @Produces("text/plain")
    public String getMessage(@Context HttpServletRequest request) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) a.getPrincipal();

        if (request.isUserInRole("ROLE_ADMIN")) {
            return "Hello, admin " + userDetails.getUsername() + "!";
        } else {
            return "Hello, user " + userDetails.getUsername() + "!";
        }
    }

}