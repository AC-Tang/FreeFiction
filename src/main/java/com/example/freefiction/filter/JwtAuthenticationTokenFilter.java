package com.example.freefiction.filter;

import com.example.freefiction.service.AuthCustomUserDetailsService;
import com.example.freefiction.utils.constant.TokenBlacklist;
import com.example.freefiction.utils.jjwt.JJwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenBlacklist tokenBlacklist;
    @Autowired
    private JJwtUtil jwtUtils;
    @Autowired
    private AuthCustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (tokenBlacklist.isBlacklisted(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            try {

                Claims claims = jwtUtils.parseClaims(token);
                /*
                String username = (String) claims.get("userid");
                */
                String username =jwtUtils.getUserFromToken("userid");

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);

                //自动续期
                if (jwtUtils.shouldRefresh(claims.getExpiration())) {
                    String role = (String) claims.get("role");
                    String newToken = jwtUtils.generateToken(username, role);
                    response.setHeader("X-Refresh-Token", newToken);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
