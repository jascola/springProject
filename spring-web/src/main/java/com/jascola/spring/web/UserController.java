package com.jascola.spring.web;

import com.jascola.spring.business.bo.UserBo;
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

    @PostMapping("/user")
    @ResponseBody
    public String postUser(){
        return "post";
    }

    @GetMapping("/user")
    public String indexFtl(UserBo userBo){
        return "index.ftl";
    }

}
