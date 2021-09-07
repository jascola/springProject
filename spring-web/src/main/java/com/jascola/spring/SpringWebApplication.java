package com.jascola.spring;

import com.jascola.spring.business.bo.PetBo;
import com.jascola.spring.business.bo.UserBo;
import com.jascola.spring.business.config.MySpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
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
        ConfigurableApplicationContext run = SpringApplication.run(SpringWebApplication.class, args);
        //@Configuration(proxyBeanMethods=false)
        //设置为false则不用spring代理 com.jascola.spring.business.config.MySpringConfig@41a6d121
        //true则配置类使用代理 com.jascola.spring.business.config.MySpringConfig$$EnhancerBySpringCGLIB$$94e2f193@3e6f3bae
        //为true时调用 userBo() 方法是实际是代理类的方法，总是会检查容器中是否有返回结果，所以总是单例。
        //所以配置类中有依赖关系则不能使用false的配置，否则依赖的bean可能是新创建的而不是容器中的bean
        MySpringConfig mySpringConfig = run.getBean(MySpringConfig.class);
        System.out.println(mySpringConfig);

        PetBo petBo = run.getBean("pet",PetBo.class);
        UserBo userBo = run.getBean("user",UserBo.class);

        System.out.println(petBo==userBo.getPetBo());
    }

    @RequestMapping
    @ResponseBody
    public Map index(){
        Map<String,String> map = new HashMap<>();
        map.put("jascola","jascola");
        return map;
    }

}
