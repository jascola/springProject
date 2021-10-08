package com.jascola.spring.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 原生 servlet注入到组件
 * 方法1： @WebServlet 注解 配置文件扫包
 * 方法2： RegistrationBean
 * */
@WebServlet(urlPatterns = {"/my","/my2"},name = "myOwnServlet")
public class MyOwnServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("cccccc");

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("bbbbbb");

    }
}
