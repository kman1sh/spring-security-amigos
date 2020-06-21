package com.manish.springsecurityamigos.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // by this annotation, class will be the place where we will configure everything that has to do with security for our application.
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // I want to AUTHORIZE Requests: autherizeRequests()
        // ANY REQUEST must be AUTHENTICATED: anyRequest(), authenticated()
        // The mechanism to authorize request is Basic Auth: httpBasic()

        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }


}
