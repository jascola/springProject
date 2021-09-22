package com.jascola.spring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 测试restful风格
 * */
@Controller
@RequestMapping("/")
public class UserController {


    @DeleteMapping("/user")
    @ResponseBody
    public String deleteUser(){
        return "delete";
    }

    @PutMapping("/user")
    @ResponseBody
    public String putUser(){
        return "put";
    }

    @GetMapping("/user")
    @ResponseBody
    public String getUser(){
        return "get";
    }

    @PostMapping("/user")
    @ResponseBody
    public String postUser(){
        return "post";
    }

}
