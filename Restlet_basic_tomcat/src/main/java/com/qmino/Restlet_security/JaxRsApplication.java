package com.qmino.Restlet_security;

import org.restlet.Context;
import org.restlet.data.ChallengeScheme;
import org.restlet.security.ChallengeAuthenticator;

public class JaxRsApplication extends org.restlet.ext.jaxrs.JaxRsApplication {

    public JaxRsApplication(Context context) {
        super(context);
        this.add(new Application());
        ChallengeAuthenticator authenticator = new ChallengeAuthenticator(context, ChallengeScheme.HTTP_BASIC, "MessageService");
        authenticator.setVerifier(new ApplicationVerifier());
        setAuthenticator(authenticator);
    }
}