package com.kiwes.backend.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwes.backend.jwt.exception.JwtException;
import com.nimbusds.oauth2.sdk.ErrorObject;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    ObjectMapper objectMapper;
    private final String NO_CHECK_URL = "/oauth/token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return; // 이거 안해주면 아래로 내려가서 계속 필터를 진행해버림
        }

        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            ErrorObject errorObject = new ErrorObject(
                    "jwt_access_token", e.getMessage()
            );
            objectMapper.writeValue(response.getWriter(), errorObject);

        }
    }
}
