package com.jascola.spring.web;

import com.jascola.spring.business.bo.UserBo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

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
    public ModelAndView indexFtl(UserBo userBo){
        Map<String, String> model = new HashMap<>();
        model.put("name","jascola");
        return new ModelAndView("index.ftl",model);
    }

}
