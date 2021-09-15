package com.jascola.spring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
