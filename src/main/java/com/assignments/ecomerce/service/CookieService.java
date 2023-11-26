package com.assignments.ecomerce.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    @Autowired
    private final HttpServletRequest request;

    public CookieService(HttpServletRequest request) {
        this.request = request;
    }
    public void addLoginCookie(HttpServletResponse response, String key, String value) {
        Cookie usernameCookie = new Cookie(key, value);
        usernameCookie.setMaxAge(7 * 24 * 60 * 60); // Thời gian tồn tại của cookie (7 ngày)
        response.addCookie(usernameCookie);
    }

    public void deleteLoginCookie(HttpServletResponse response, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    cookie.setMaxAge(0); // Đặt thời gian tồn tại cookie là 0 để xóa nó
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    public String getCookieValue(String username) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(username)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
