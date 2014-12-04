
package com.qmino.Restlet_security;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Path("/message")
public class MessageService {

    @GET
    @Produces("text/plain")
//    @RolesAllowed( {"admin", "user"})
    public String getMessage(@Context SecurityContext securityContext) {
//        if (securityContext.isUserInRole("admin")) {
//            return "Hello, admin " + securityContext.getUserPrincipal().getName() + "!";
//        } else {
//            return "Hello, user " + securityContext.getUserPrincipal().getName() + "!";
//        }
        return "Hello!";
    }

}