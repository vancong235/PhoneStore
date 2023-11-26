package com.assignments.ecomerce.service.impl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var authourities = authentication.getAuthorities();
        var roles = authourities.stream().map(r ->r.getAuthority()).findFirst();

        System.out.println(roles);
        if(roles.orElse("").equals("ADMIN")){
            response.sendRedirect("/admin-page");
        }
        else if(roles.orElse("").equals("USER")){
            response.sendRedirect("/user-page");
        }
        else if(roles.orElse("").equals("MANAGER")){
            response.sendRedirect("/manager-page");
        }
        else {
            response.sendRedirect("/error");
        }
    }
}
