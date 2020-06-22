package com.aptitude.interceptor;

import com.aptitude.domain.Admin;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {

        String url = request.getRequestURI();
        System.out.println("url:" + url);

        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("ADMIN");
        if (admin != null) {
            return true;
        }

        request.setAttribute("tips", "您还未登录，请先登录");
        request.getRequestDispatcher("/login").forward(request, response);
        return false;
    }
}
