package com.qmino.RESTeasy_security;

import org.jboss.resteasy.annotations.interception.Precedence;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.Base64;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
* This interceptor verify the access permissions for a user
* based on username and passowrd provided in request
* */
@Provider
@ServerInterceptor
@Precedence("SECURITY")
public class SecurityFilter implements ContainerRequestFilter
{
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());;
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());;
    private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<Object>());;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker)
                requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
        //Access denied for all
        if(method.isAnnotationPresent(DenyAll.class))
        {
            requestContext.abortWith(ACCESS_FORBIDDEN);
            return;
        }

        //Get request headers
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();

        //Fetch authorization header
        final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

        //If no authorization information present; block access
        if(authorization == null || authorization.isEmpty())
        {
            requestContext.abortWith(ACCESS_DENIED);
            return;
        }

        //Get encoded username and password
        final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        //Decode username and password
        String usernameAndPassword = null;
        try {
            usernameAndPassword = new String(Base64.decode(encodedUserPassword));
        } catch (IOException e) {
            requestContext.abortWith(SERVER_ERROR);
            return;
        }

        //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        //Verifying Username and password
        System.out.println(username);
        System.out.println(password);

        //Process user info
        User user = null;
        if (username.equals("gerrit") && password.equals("gerrit")) {
            user = new User(username, Arrays.asList("admin"));
        } else if (username.equals("paul") && password.equals("paul")) {
            user = new User(username, Arrays.asList("user"));
        } else {
            requestContext.abortWith(ACCESS_DENIED);
            return;
        }
        //Set the security context
        requestContext.setSecurityContext(new MySecurityContext(user));

        //Access allowed for all
        if(method.isAnnotationPresent(PermitAll.class)) {
            return;
        }
        //Verify user access
        if(method.isAnnotationPresent(RolesAllowed.class))
        {
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

            //Is user valid?
            if( ! isUserAllowed(user, rolesSet))
            {
                requestContext.abortWith(ACCESS_DENIED);
            }
        }
    }

    private boolean isUserAllowed(final User user, final Set<String> rolesSet)
    {
        for (String role : rolesSet) {
            if (user.hasRole(role)) {
                return true;
            }
        }
        return false;
    }

}
