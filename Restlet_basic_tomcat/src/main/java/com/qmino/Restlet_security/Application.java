package com.qmino.Restlet_security;

import java.util.HashSet;
import java.util.Set;

public class Application extends javax.ws.rs.core.Application {

    public Set<Class<?>> getClasses() {
        Set<Class<?>> rrcs = new HashSet<Class<?>>();
        rrcs.add(MessageService.class);
        return rrcs;
    }
}