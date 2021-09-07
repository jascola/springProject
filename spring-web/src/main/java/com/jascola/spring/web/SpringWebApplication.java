package com.jascola.spring.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Controller
@RequestMapping("/")
public class SpringWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebApplication.class, args);
    }

    @RequestMapping
    @ResponseBody
    public Map index(){
        Map<String,String> map = new HashMap<>();
        map.put("jascola","jascola");
        return map;
    }

}
