package com.esprit.cloudcraft.filter;


import com.esprit.cloudcraft.repository.userDao.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor // ysn3 ay construct mn ay final ndeclariwh lahne
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private  final JwtService jwtService;

    private  final UserDetailsService us;
    private final TokenRepository authTokenRepository;

    @Override
    protected void doFilterInternal(  @NonNull  HttpServletRequest request, @NonNull  HttpServletResponse response,
                                      @NonNull   FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            email = jwtService.extractUsernameFromToken(token);
        }
        if (email != null & SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = us.loadUserByUsername(email);
            var isValideToken= authTokenRepository.findByToken(token).map(t->!t.isExpired()&& !t.isRevoked()).orElse(false);
            if(jwtService.validateToken(token, userDetails) && isValideToken) {
                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}






