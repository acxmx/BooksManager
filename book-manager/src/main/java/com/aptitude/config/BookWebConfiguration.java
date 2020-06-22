package com.aptitude.config;


import com.aptitude.domain.Admin;
import com.aptitude.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Configuration
public class BookWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login.do", "/login", "/", "index", "/asserts/**");
    }
}





/*
@Configuration
public class BookWebConfiguration extends WebMvcConfigurationSupport {

@Autowired
    private LoginInterceptor loginInterceptor;


    @Bean
    public HandlerInterceptor getInterceptor () {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String url = request.getRequestURI();
                System.out.println("url:" + url);

                HttpSession session = request.getSession();
                Admin admin = (Admin) session.getAttribute("ADMIN");
                if (admin != null) {
                    return true;
                }
                request.setAttribute("tips", "您还没有登录，请先登录");
                request.getRequestDispatcher(request.getContextPath() + "toLogin")
                        .forward(request, response);

                System.out.println("request.getContextPath: " + request.getContextPath());
//                response.sendRedirect(request.getContextPath() + "/toLogin");
                return false;
            }
        };
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(getInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/toLogin", "/login", "/login.do", "/login.html","/js/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("forward:/global/login") ;
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }
}

*/
