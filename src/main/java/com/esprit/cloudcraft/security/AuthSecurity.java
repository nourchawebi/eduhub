package com.esprit.cloudcraft.security;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthSecurity {
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
