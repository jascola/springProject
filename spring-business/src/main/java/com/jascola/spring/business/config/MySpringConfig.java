package com.jascola.spring.business.config;

import com.jascola.spring.business.bo.PetBo;
import com.jascola.spring.business.bo.UserBo;
import com.jascola.spring.business.importconf.ImportBean;
import com.jascola.spring.business.importconf.ImportBeanDefinitionRegistrarImpl;
import com.jascola.spring.business.importconf.ImportSelectorImpl;
import com.jascola.spring.business.property.MainSettingProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

//告诉SpringBoot这是一个配置类 == 配置文件
@Configuration(proxyBeanMethods=true)
//引入spring配置文件xml
@ImportResource(value = "classpath:spring-res.xml")
//Full:外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象
//如果@Configuration(proxyBeanMethods = true)代理对象调用方法。SpringBoot总会检查这个组件是否在容器中有。

@EnableConfigurationProperties({MainSettingProperties.class})
@PropertySource(value = "classpath:main-setting.properties")
/**
 * 容器中导入bean的方式之一
 * 1、@Import
 * 2、继承ImportBeanDefinitionRegistrar 手动注册
 * 3、继承ImportSelector
 * */

//ImportSelectorImpl.class, ImportBeanDefinitionRegistrarImpl.class 这两个类不会注册为组件，只会注册实现方法中的类。
@Import({ImportBean.class, ImportSelectorImpl.class, ImportBeanDefinitionRegistrarImpl.class})
public class MySpringConfig {

    @Bean("user")
    //以参数形式传入的bean，会从容器取
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
