package com.jascola.spring;

import com.jascola.spring.business.bo.PetBo;
import com.jascola.spring.business.bo.UserBo;
import com.jascola.spring.business.config.MySpringConfig;
import com.jascola.spring.business.property.MainSettingProperties;
import com.jascola.spring.interceptor.PersonInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;

@SpringBootApplication
@Controller
@RequestMapping("/")

public class SpringWebApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringWebApplication.class, args);
        //@Configuration(proxyBeanMethods=false)
        //设置为false则不用spring代理 com.jascola.spring.business.config.MySpringConfig@41a6d121
        //true则配置类使用代理 com.jascola.spring.business.config.MySpringConfig$$EnhancerBySpringCGLIB$$94e2f193@3e6f3bae
        //为true时调用 userBo() 方法是实际是代理类的方法，总是会检查容器中是否有返回结果，所以总是单例。
        //所以配置类中有依赖关系则不能使用false的配置，否则依赖的bean可能是新创建的而不是容器中的bean
        MySpringConfig mySpringConfig = run.getBean(MySpringConfig.class);
        System.out.println(mySpringConfig);

//        PetBo petBo = run.getBean("pet",PetBo.class);
//        UserBo userBo = run.getBean("user",UserBo.class);
//
//        System.out.println(petBo==userBo.getPetBo());

        //2、查看容器里面的组件
        String[] names = run.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);

        }
        MainSettingProperties settingProperties = run.getBean("com.jascola-com.jascola.spring.business.property.MainSettingProperties",MainSettingProperties.class);

        System.out.println(settingProperties.toString());
    }

    @RequestMapping
    @ResponseBody
    public Map index(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("jascola", request.getAttribute("jascola"));

        return map;
    }


    /**
     * 自定义bean
     * ConditionalOnMissingBean(HiddenHttpMethodFilter.class)
     * WebMvcAutoConfiguration中的OrderedHiddenHttpMethodFilter 不生效
     * */
    @Bean
    @ConditionalOnProperty(prefix = "spring.mvc.hiddenmethod.filter", name = "enabled",matchIfMissing = true)
    public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
        OrderedHiddenHttpMethodFilter filter = new OrderedHiddenHttpMethodFilter();
        //自定义表单传参
        filter.setMethodParam("caonima");
        return filter;
    }

    /**
     * 自定义参数类型转换器
     * 实现Converter接口，并通过FormatterRegistry 注册到 spring中
     * spring解析参数时从类型转换器集合中找匹配的，  《Class<S> sourceType, Class<T> targetType》为key。
     *
     *
     * DataBinder binder = binderFactory.createBinder(request, null, attributeName);
     * ConversionService conversionService = binder.getConversionService();
     * */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, PetBo>() {
            @Override
            public PetBo convert(String source) {
                if(!StringUtils.isEmpty(source)){
                    String[] strs = source.split(",");
                    PetBo petBo = new PetBo();
                    petBo.setAge(Long.valueOf(strs[1]) );
                    petBo.setName(strs[0]);
                    return petBo;
                }
                return null;
            }

        });

        /*同key  《Class<S> sourceType, Class<T> targetType》 的 优先级Formatter>Converter */
        registry.addFormatter(new Formatter<PetBo>() {
            @Override
            public PetBo parse(String text, Locale locale) throws ParseException {
                if(!StringUtils.isEmpty(text)){
                    String[] strs = text.split("-");
                    PetBo petBo = new PetBo();
                    petBo.setAge(Long.valueOf(strs[1]) );
                    petBo.setName(strs[0]);
                    return petBo;
                }
                return null;
            }

            @Override
            public String print(PetBo object, Locale locale) {
                return object.getName()+"--------"+object.getAge();
            }
        });
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        converters.add(new MyOwnHttpMessageConvert());

    }

    public static class MyOwnHttpMessageConvert implements HttpMessageConverter<UserBo> {

        /**
         * 针对@RequestBody注解 会使用 RequestResponseBodyMethodProcessor
         *  参数解析会使用HttpMessageConverters
         * */

        @Override
        public boolean canRead(Class<?> clazz, MediaType mediaType) {
            return false;
        }
        /**
         * 是否可以写，以及什么条件下可以写
         * mediaType判空，*\*都放过，为true
         */
        @Override
        public boolean canWrite(Class<?> clazz, MediaType mediaType) {
            return true;
        }
        /**
         * 服务端能提供哪些协议
         */
        @Override
        public List<MediaType> getSupportedMediaTypes() {
          return  MediaType.parseMediaTypes("application/good--job");
        }

        @Override
        public UserBo read(Class<? extends UserBo> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
            return null;
        }

        @Override
        public void write(UserBo userBo, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

            String s = userBo.getName() + "----------" + userBo.getAge();

            OutputStream outputStream = outputMessage.getBody();
            outputStream.write(s.getBytes(StandardCharsets.UTF_8));
        }
    }


    /**
     * 注册视图解析器
     * */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.freeMarker();
    }
    // Configure FreeMarker...

    /**
     * Freemarker配置，可以自动装配。
     * */
//    @Bean
//    public FreeMarkerConfigurer freeMarkerConfigurer() {
//        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
//        configurer.setTemplateLoaderPaths("classpath:/freemarker/");
//        configurer.setDefaultEncoding("UTF-8");
//        return configurer;
//    }

    /**
     * 注册拦截器到组建中
     * */
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new PersonInterceptor()).addPathPatterns("/person/**")
//                .excludePathPatterns("")
        ;

    }

}
