package com.jascola.spring.web;

import com.jascola.spring.business.bo.UserBo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class PersonController {


    @DeleteMapping("/person")
    @ResponseBody
    public UserBo deletePerson(UserBo userBo){
        return userBo;
    }



}
