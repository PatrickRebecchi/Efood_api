package com.patrick.efoodapi.security;


import com.patrick.efoodapi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final com.patrick.efoodapi.service.JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {

                String token = header.substring(7);

                // 🔐 valida token
                if (jwtService.isTokenValid(token)) {

                    String email = jwtService.getEmailFromToken(token);
                    String role = jwtService.getRoleFromToken(token);


                    if (email != null && role != null &&
                            SecurityContextHolder.getContext().getAuthentication() == null) {

                        var authorities = List.of(
                                new SimpleGrantedAuthority("ROLE_" + role)
                        );

                        var authentication = new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                authorities
                        );

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

        } catch (Exception e) {

            System.out.println("Erro no JWT: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}