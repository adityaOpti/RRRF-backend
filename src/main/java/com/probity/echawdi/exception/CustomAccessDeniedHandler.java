package com.probity.echawdi.exception;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        String responseBody = String.format(
                "{"
                + "\"status\":403,"
                + "\"error\":\"Forbidden\","
                + "\"messageMarathi\":\"प्रवेश नाकारला! तुम्हाला या पृष्ठावर प्रवेश नाही.\","
                + "\"messageEnglish\":\"Access Denied! You don't have permission to access this page.\","
                + "\"requestedUrl\":\"%s\","
                + "\"timestamp\":\"%s\""
                + "}",
                request.getRequestURI(),
                new java.util.Date()
        );

        response.getWriter().write(responseBody);
    }
    
    
    public void handleRefreshTokenExpired(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        String responseBody = String.format(
                "{"
                + "\"status\":403,"
                + "\"error\":\"Forbidden\","
                + "\"messageMarathi\":\"तुमचे सत्र कालबाह्य झाले आहे. कृपया पुन्हा लॉगिन करा.\","
                + "\"messageEnglish\":\"Refresh token has expired. Please login again.\","
                + "\"requestedUrl\":\"%s\","
                + "\"timestamp\":\"%s\""
                + "}",
                request.getRequestURI(),
                new java.util.Date()
        );

        response.getWriter().write(responseBody);
    }
}