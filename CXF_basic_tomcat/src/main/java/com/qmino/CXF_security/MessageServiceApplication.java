package com.qmino.CXF_security;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gerrit on 03.12.14.
 */
public class MessageServiceApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(MessageService.class);
        return s;
    }}
