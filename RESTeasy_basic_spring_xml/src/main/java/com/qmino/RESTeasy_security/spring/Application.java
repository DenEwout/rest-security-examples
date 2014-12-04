package com.qmino.RESTeasy_security.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import javax.ws.rs.ext.Provider;

/**
 * The Spring application context (java based config).
 */
@Configuration // This class is a source of bean definitions
@ComponentScan(basePackages = {"com.qmino.RESTeasy_security"},
               includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Provider.class))
public class Application {

}