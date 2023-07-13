package com.bookstore.securitymodule.filters;

import com.bookstore.securitymodule.configurations.AppSecurityConfig;
import com.bookstore.securitymodule.service.SecurityUserService;
import com.bookstore.securitymodule.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthRequestFilter extends OncePerRequestFilter {

    private final SecurityUserService securityUserService;
    private final JwtUtil jwtUtil;
    private final AppSecurityConfig appSecurityConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String jwt = null;

        if (appSecurityConfig.getIsJwtAuthEnabled()) {
            username = jwtUtil.extractUsernameFromRequest(request);
            jwt = jwtUtil.getJwtToken(request);
        }
        if (appSecurityConfig.getIsBasicAuthEnabled() && !Optional.ofNullable(username).isPresent()) {
            if (validateBasicAuth(request, response)) return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            authenticateJwt(request, username, jwt);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateJwt(HttpServletRequest request, String username, String jwt) {
        UserDetails userDetails = securityUserService.loadUserByUsername(username);
        if (jwtUtil.validateToken(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    private boolean validateBasicAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (null != authorizationHeader && authorizationHeader.contains("Basic")) {
            String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
            byte[] credentialsBytes = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credentialsBytes);
            String[] splitCredentials = credentials.split(":");
            String basicUsername = splitCredentials[0];
            String basicPassword = splitCredentials[1];
            if (appSecurityConfig.getUsername().equals(basicUsername) && appSecurityConfig.getPassword().equals(basicPassword)) {
                UserDetails userDetails;
                String username = request.getHeader("username");
                if (StringUtils.hasText(username)) {
                    userDetails = securityUserService.loadUserByUsername(username);
                } else {
                    userDetails = User.withUsername(basicUsername).password(basicPassword).authorities(Arrays.asList()).accountExpired(false).accountLocked(false).credentialsExpired(false).disabled(false).build();
                }
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                return false;
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Content-Type", "text/plain");
            response.getWriter().write("Unauthorized Request");
            response.setHeader("WWW-Authenticate", "Basic realm=\"Restricted Area\"");
            return true;
        }
        return false;
    }
}
