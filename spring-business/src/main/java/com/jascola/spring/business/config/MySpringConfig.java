package com.jascola.spring.business.config;

import com.jascola.spring.business.bo.PetBo;
import com.jascola.spring.business.bo.UserBo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//告诉SpringBoot这是一个配置类 == 配置文件
@Configuration(proxyBeanMethods=true)
//Full:外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象
//如果@Configuration(proxyBeanMethods = true)代理对象调用方法。SpringBoot总会检查这个组件是否在容器中有。
public class MySpringConfig {

    @Bean("user")
    //以参数形式传入的bean，参数名称要和bean名称一致
    public UserBo userBo(PetBo pet){

        UserBo userBo = new UserBo();
        userBo.setAge(1L);
        userBo.setName("jascola");
        userBo.setPetBo(pet);
        return  userBo;
    }

    @Bean("pet")
    public PetBo petBo(){

        PetBo petBo = new PetBo();

        petBo.setAge(1L);
        petBo.setName("ahuang");

        return  petBo;
    }
}
