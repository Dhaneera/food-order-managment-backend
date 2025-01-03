package com.foodmanagement.Config;

import com.foodmanagement.Service.impl.CustomUserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenGenerator tokenGenerator;

    @Autowired
    private CustomUserDetailServiceImpl customUserDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = getJwtFromRequest(request);

        if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
            String username = tokenGenerator.getUsernameFromJWT(token);

            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            String refreshToken = tokenGenerator.generateRefreshToken(authenticationToken);
            int refreshTokenExpirationInSeconds = (int) (SecurityConstance.JWT_REFRESH_EXPIRATION / 1000);


            String cookieName = "RefreshToken";
            String cookieValue = refreshToken;
            String path = "/";
            boolean httpOnly = true;
            boolean secure = true;
            int maxAgeInSeconds = (int) (SecurityConstance.JWT_REFRESH_EXPIRATION / 1000);

            Cookie newRefreshTokenCookie = new Cookie(cookieName, cookieValue);
            newRefreshTokenCookie.setHttpOnly(httpOnly);
            newRefreshTokenCookie.setSecure(secure); // Set secure flag to true
            newRefreshTokenCookie.setPath(path);
            newRefreshTokenCookie.setMaxAge(maxAgeInSeconds);
            response.setHeader("Set-Cookie", String.format("%s=%s; Path=%s; Max-Age=%d; HttpOnly; Secure", cookieName, cookieValue, path, refreshTokenExpirationInSeconds));
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
