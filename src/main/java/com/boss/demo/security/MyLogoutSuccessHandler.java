package com.boss.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if( authentication!=null && authentication.getPrincipal() instanceof CustomUser){ //如果認證不是Customer有可能anonymous
            CustomUser customUser = (CustomUser)authentication.getPrincipal();
            if( "consumer".equals(customUser.getLevel() )){
                response.sendRedirect("/front");
                return;
            }
        }
        response.sendRedirect("/web/login");
    }
}