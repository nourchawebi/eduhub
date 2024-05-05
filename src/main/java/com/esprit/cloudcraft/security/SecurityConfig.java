package com.esprit.cloudcraft.security;

import jakarta.annotation.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import com.esprit.cloudcraft.filter.JwtAuthenticationFilter;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final LogoutHandler logoutHandler;
    private final JwtAuthenticationFilter  jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {    // Disable Cross-Origin Resource Sharing (CORS)
        http.cors(withDefaults())
                // Disable Cross-Site Request Forgery (CSRF) protection
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                                .requestMatchers(auth_whitelist).permitAll()
                                .requestMatchers("/register/**").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/getenabled").permitAll()
                                .requestMatchers("/getclasstypes").permitAll()
                                .requestMatchers("/files/**").permitAll()
                                .requestMatchers("/user/update/email/verify").permitAll()


                                .anyRequest().authenticated()
                        //will not create or use HTTP sessions to maintain user state
                )

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                // Add JWT authentication filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)





        ;


        return http.build();
    }
    private static final String [] auth_whitelist={
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui-html"
    };

}
