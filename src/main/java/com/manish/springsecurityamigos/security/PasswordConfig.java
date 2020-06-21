package com.manish.springsecurityamigos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    /**
     * PasswordEncoder is a Interface, most important method is:
     * String encode(CharSequence var1);it encode the password into random characters
     * <p>
     * So the most Common password encoder that implement this interface is: BCryptPasswordEncoder
     * one of the constructor of BCryptPasswordEncoder takes int Strength as arg to define how strong encoding should be done
     * <p>
     * Note: once we have created Bean of PasswordEncoder, "There is no PasswordEncoder" error will be gone even if we havn't used it
     * to encode password anywhere yet.
     * But we will get a new console WARNING saying "Encoded password does not look like BCrypt" (BCrypt becoz our bean is of that type)
     * and it will still not let us log in.
     * why? Once Spring security sees a password, it always assume that it is encoded and also assume that you have mentioned
     * PasswordEncoder for this somewhere. (i.e @Beans).
     * Every encoder has a signature, so spring matches it with your password (which is still a plain text, as you have just create
     * the bean but not used to encode the password).
     * Since signature does not match with the PasswordEncoder that Bean is providing,
     * Hence it gives warning on console : "Encoded password does not look like BCrypt"
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
