package com.jascola.spring.exceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @ExceptionHandler
     * 注解标注会由ExceptionHandlerExceptionResolver处理
     * 异常处理后也是返回ModelAndView或者String（视图）
     * */
    @ExceptionHandler({NullPointerException.class})
    public ModelAndView handlerException(Exception e){

        System.out.println(e);

        Map<String, String> model = new HashMap<>();
        model.put("name","jascola");
        return new ModelAndView("index",model);
    }

}
