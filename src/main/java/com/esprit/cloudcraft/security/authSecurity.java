package com.esprit.cloudcraft.security;

import com.esprit.cloudcraft.services.userService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class authSecurity {
    @Resource
    UserDetailsService ss;
  /*  @Bean
    AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setUserDetailsService(ss);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }*/

}
