package com.jascola.spring.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PersonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

//        请求转发
        request.setAttribute("jascola","jascola");
        request.getRequestDispatcher("/").forward(request,response);

//        重定向
//        response.sendRedirect("/");
        return false;
    }


}
