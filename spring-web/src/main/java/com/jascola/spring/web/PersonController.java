package com.jascola.spring.web;

import com.jascola.spring.business.bo.UserBo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 1、测试参数类型转换器
 * 2、测试参数返回
 * 3、测试内容协商
 * */
@Controller
@RequestMapping("/")
public class PersonController {


    @DeleteMapping("/person")
    @ResponseBody
    public UserBo deletePerson(UserBo userBo){
        return userBo;
    }



}
