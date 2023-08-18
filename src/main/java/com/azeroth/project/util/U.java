package com.azeroth.project.util;

import com.azeroth.project.config.PrincipalDetails;
import com.azeroth.project.domain.UserDomain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class U {
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attrs.getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static UserDomain getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails) {
            PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
            return userDetails.getUser();
        }
        return null;
    }
}
