package com.user.jobapplicationtracker.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.user.jobapplicationtracker.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {
    
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            // parse token
            String token = parseJwt(request);

            // if token is not null and valid then extract the username FROM the token
            if (token != null && jwtUtil.validateJwtToken(token)) {
                String username = jwtUtil.getUserToken(token);

                // use user's username to LOAD the actual user
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // build an Authentication object representing this verified user
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // register it so Spring Security treats this request as authenticated
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // pass the single request to the next stage ()
            filterChain.doFilter(request, response); // <- runs regardless of whether the if succeeded
        } catch (Exception e) {
            log.error("JWT filter error: {}", e.getMessage());
            filterChain.doFilter(request, response);
        }
    }

    private String parseJwt(HttpServletRequest request) {

        String headerAuth = request.getHeader("Authorization");

        // if header has Bearer <token> only return the token
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
