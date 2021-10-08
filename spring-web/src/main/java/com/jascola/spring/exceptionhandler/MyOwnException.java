package com.jascola.spring.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ResponseStatus 注解使用 ResponseStatusExceptionResolver解析
 * 把@ResponseStatus 注解信息组装成ModelAndView 再调用sendError
 * */
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "no bb")
public class MyOwnException extends RuntimeException{

    public MyOwnException(){
    }

    public MyOwnException(String message){
        super(message);
    }

}
