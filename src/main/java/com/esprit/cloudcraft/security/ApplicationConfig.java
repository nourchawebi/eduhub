package com.esprit.cloudcraft.security;

import com.esprit.cloudcraft.repository.userDao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.web.filter.CorsFilter;
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository repository;
    /************** Define a UserDetailsService bean that loads user details from the UserRepository ************/
    @Bean
    public UserDetailsService userDetailsService()
    {
        return  username -> repository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }
/************ Define an AuthenticationProvider bean that uses the custom UserDetailsService and PasswordEncoder***************/
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder encoder()
    {
        // Return an instance of BCryptPasswordEncoder as the PasswordEncoder
        return new BCryptPasswordEncoder();
    }

}
