package com.jascola.spring.web;

import com.jascola.spring.business.bo.UserBo;
import com.jascola.spring.exceptionhandler.MyOwnException;
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
        String str = null;
        str.toString();
        return "delete";
    }

    @PutMapping("/user")
    @ResponseBody
    public String putUser(){
        throw new MyOwnException();
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
        return new ModelAndView("index",model);
    }

}
