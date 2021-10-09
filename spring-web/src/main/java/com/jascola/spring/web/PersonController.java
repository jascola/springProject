package com.jascola.spring.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jascola.spring.business.biz.ResourceAvBookService;
import com.jascola.spring.business.biz.impl.ResourceAvBookBiz;
import com.jascola.spring.business.bo.UserBo;
import com.jascola.spring.business.entity.AvBookEntity;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private ResourceAvBookService resourceAvBookService;

    @DeleteMapping("/person")
    @ResponseBody
    public UserBo deletePerson(UserBo userBo){
        return userBo;
    }

    @PostMapping("/person")
    @ResponseBody
    public UserBo postPerson(UserBo userBo){
        return userBo;
    }

    @GetMapping("/person")
    @ResponseBody
    public Page<AvBookEntity> getPerson(Long id){
        Page<AvBookEntity> page = new Page<>();
        page.setCountId("1");
        page.setSize(100L);
        return resourceAvBookService.page(page,null);
    }

}
