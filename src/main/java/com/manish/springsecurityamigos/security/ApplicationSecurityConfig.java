package com.manish.springsecurityamigos.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.manish.springsecurityamigos.security.ApplicationUserPermission.*;
import static com.manish.springsecurityamigos.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity // by this annotation, class will be the place where we will configure everything that has to do with security for our application.
@EnableGlobalMethodSecurity(prePostEnabled = true) //annotation to tell that we want to use PreAuthorize() annotation based configuration

public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired //instead of Autowiring the property directly using constructor to Autowire : NEW PRACTICE
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // I want to AUTHORIZE Requests: authorizeRequests()
        // ANY REQUEST must be AUTHENTICATED: anyRequest(), authenticated()
        // The mechanism to authorize request is Basic Auth: httpBasic()

        // There are two ways to do Authority based Authentication: Using antMatchers(), annotation based in the actual method(preferred way).
        // When to mention httpMethod along with antPatterns ? when we want allow Authorized user to perform only specific set of httpMethod request.
        // e.g let's ssy user ko authority hai antPatterns wale api ko access krne ki BUT we want ki wo sirf GET method se hi API access kr paye.
        // NOTE: TO perform permission/Authority Authentication, user only "role aware" will not work as they dont know anything about permission/Authority
        // They only know roles. So for this add Authorities to user to perform permission/Authority Authentication.

        // To use PERMISSION(authority) and ROLE Based Authentication on method level with annotation: @PreAuthorized("String"),
        // In parameter we can say things like: hasRole(), hasAnyRole(), hasAuthority(), hasAnyAuthority()
        // E.g: hasRole('ROLE_roleHere'), hasAnyRole('ROLE_roleHere'), hasAuthority('permission'), hasAnyAuthority('permission')

        http
                .csrf().disable() //TODO: POST, PUT, DELETE will work after this.
                .authorizeRequests()
                // root, page name "index",all files in /css folder, all file in /js folder should to permitted(make accessible) to all
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll() //whitelisting APIs/url
                .antMatchers("/api/**").hasRole(STUDENT.name()) //Role Based AUTHORIZATION
//                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) // User mai bhi ye Authority mention hona chaiye. but just roles but also separate authority.
//                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        // BUILD a user details

        // when we pass define roles in String, the role is internally Converted into GrantedAuthority Object. with naming convention: (ROLE_+"roleName")
        // Instead of passing role which BTS gets converted into to authorities, hum khud hi role ko GratedAuthority
        // mai wrap krke (in accordance with convention), authorities() method m pass kr skte hai.


        UserDetails manishKumarUser = User.builder()
                .username("manish")
//                .password("password") // must be encoded otherwise throw error "There is no PasswordEncoder mapped"
                .password(passwordEncoder.encode("password")) // debug at this line to see encoded pwd.(use Evaluate Expression Alt+F8)
//                .roles("STUDENT") //internally it is ROLE_STUDENT
//                .roles(STUDENT.name()) // ROLE_STUDENT
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails lindaUser = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMIN.name()) // ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails tomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        // UserDetailsService is an interface implemented by many classes (Ctrl+click on name for more detail) and
        // below interface is one of many classes which implement this interface, so returning that class obj.

        // creates in Memory user, expect Collection or arrays of UserDetails as arg.
        return new InMemoryUserDetailsManager(
                manishKumarUser,
                lindaUser,
                tomUser
        );
    }

}
