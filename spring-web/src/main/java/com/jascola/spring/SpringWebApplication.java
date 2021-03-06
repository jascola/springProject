package com.jascola.spring;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.jascola.spring.business.bo.PetBo;
import com.jascola.spring.business.bo.UserBo;
import com.jascola.spring.business.config.MySpringConfig;
import com.jascola.spring.business.property.MainSettingProperties;
import com.jascola.spring.interceptor.PersonInterceptor;
import com.jascola.spring.servlet.MyOwnFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
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

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;

@ServletComponentScan(basePackages = "com.jascola.spring.servlet")
@SpringBootApplication
@Controller
@RequestMapping("/")

public class SpringWebApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringWebApplication.class, args);
        //@Configuration(proxyBeanMethods=false)
        //?????????false?????????spring?????? com.jascola.spring.business.config.MySpringConfig@41a6d121
        //true???????????????????????? com.jascola.spring.business.config.MySpringConfig$$EnhancerBySpringCGLIB$$94e2f193@3e6f3bae
        //???true????????? userBo() ????????????????????????????????????????????????????????????????????????????????????????????????????????????
        //????????????????????????????????????????????????false???????????????????????????bean??????????????????????????????????????????bean
        MySpringConfig mySpringConfig = run.getBean(MySpringConfig.class);
        System.out.println(mySpringConfig);

//        PetBo petBo = run.getBean("pet",PetBo.class);
//        UserBo userBo = run.getBean("user",UserBo.class);
//
//        System.out.println(petBo==userBo.getPetBo());

        //2??????????????????????????????
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
     * ?????????bean
     * ConditionalOnMissingBean(HiddenHttpMethodFilter.class)
     * WebMvcAutoConfiguration??????OrderedHiddenHttpMethodFilter ?????????
     * */
    @Bean
    @ConditionalOnProperty(prefix = "spring.mvc.hiddenmethod.filter", name = "enabled",matchIfMissing = true)
    public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
        OrderedHiddenHttpMethodFilter filter = new OrderedHiddenHttpMethodFilter();
        //?????????????????????
        filter.setMethodParam("caonima");
        return filter;
    }

    /**
     * ??????????????????????????????
     * ??????Converter??????????????????FormatterRegistry ????????? spring???
     * spring?????????????????????????????????????????????????????????  ???Class<S> sourceType, Class<T> targetType??????key???
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

        /*???key  ???Class<S> sourceType, Class<T> targetType??? ??? ?????????Formatter>Converter */
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
         * ??????@RequestBody?????? ????????? RequestResponseBodyMethodProcessor
         *  ?????????????????????HttpMessageConverters
         * */

        @Override
        public boolean canRead(Class<?> clazz, MediaType mediaType) {
            return false;
        }
        /**
         * ????????????????????????????????????????????????
         * mediaType?????????*\*???????????????true
         */
        @Override
        public boolean canWrite(Class<?> clazz, MediaType mediaType) {
            return true;
        }
        /**
         * ??????????????????????????????
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
     * ?????????????????????
     * */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.freeMarker();
    }
    // Configure FreeMarker...

    /**
     * Freemarker??????????????????????????????
     * */
//    @Bean
//    public FreeMarkerConfigurer freeMarkerConfigurer() {
//        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
//        configurer.setTemplateLoaderPaths("classpath:/freemarker/");
//        configurer.setDefaultEncoding("UTF-8");
//        return configurer;
//    }

    /**
     * ???????????????????????????
     * */
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new PersonInterceptor()).addPathPatterns("/person/**")
//                .excludePathPatterns("")
        ;

    }


    /**
     * ??????RegistrationBean??????
     * */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new MyOwnFilter());
//        filterRegistrationBean.setUrlPatterns(Arrays.asList("/zz","/zz2"));
        filterRegistrationBean.setServletNames(Collections.singletonList("myOwnServlet"));
        return  filterRegistrationBean;
    }

    /**
     * ??????Druid?????????????????????
     * */
    @Bean
    public ServletRegistrationBean<StatViewServlet> servletRegistrationBean(){
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.setUrlMappings(Collections.singletonList("/druid/*"));
        return servletRegistrationBean;
    }

    /**
     * Web???Spring????????????
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistraWebStatFilterBean(){
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        return filterRegistrationBean;
    }
}
