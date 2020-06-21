package com.manish.springsecurityamigos.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
// by this annotation, class will be the place where we will configure everything that has to do with security for our application.
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired //instead of Autowiring the property directly using constructor to Autowire : NEW PRACTICE
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // I want to AUTHORIZE Requests: autherizeRequests()
        // ANY REQUEST must be AUTHENTICATED: anyRequest(), authenticated()
        // The mechanism to authorize request is Basic Auth: httpBasic()

        http
                .authorizeRequests()
                // root, page name "index",all files in /css folder, all file in /js folder should to permitted(make accessible) to all
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll() //whitelisting APIs/url
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        // BUILD a user details
        UserDetails manishKumarUser = User.builder()
                .username("manish")
//                .password("password") // must be encoded otherwise throw error "There is no PasswordEncoder mapped"
                .password(passwordEncoder.encode("password")) // debug at this line to see encoded pwd.(use Evaluate Expression Alt+F8)
                .roles("STUDENT") //internally it is ROLE_STUDENT
                .build();

        //UserDetailsService is an interface implemented by many classes and interfaces (Ctrl+click on name for more detail)
        // below is one of many classes which implement this interface, so returning that class obj.

        // creates in Memory user, expect Collection or arrays of UserDetails as arg.
        return new InMemoryUserDetailsManager(
                manishKumarUser
        );
    }

}
