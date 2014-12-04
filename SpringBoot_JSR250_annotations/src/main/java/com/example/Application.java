package com.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Ewout on 4/12/2014.
 */
@ComponentScan
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true,jsr250Enabled = true)
public class Application {

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new MySecurityConfigurer();
    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    public static class MySecurityConfigurer extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(AuthenticationManagerBuilder builder)throws Exception {
            builder.inMemoryAuthentication()
                    .withUser("user").password("user").roles("USER")
                    .and().withUser("admin").password("admin").roles("ADMIN");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}