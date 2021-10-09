package com.jascola.spring.business.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.mysql.fabric.xmlrpc.base.Param;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

/**
 * 配置Druid数据源
 * 并开启一系类功能
 * */
@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties("druid.datasource")
    public DataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl("jdbc:mysql://192.168.0.102:3306/resource");
//        dataSource.setUsername("root");
//        dataSource.setPassword("123456");
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        //打开Druid的监控统计功能
        dataSource.setFilters("stat,wall");
        return dataSource;
    }

    /**
     * mybatis分页拦截器配置
     * 添加一个MybatisPlusInterceptor bean，在这个拦截器里配置 分页拦截器
     * */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){

        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(paginationInterceptor);

        return mybatisPlusInterceptor;
    }




}
