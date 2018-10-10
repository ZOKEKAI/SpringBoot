package com.zoke.springboot.common.config;

import com.zoke.springboot.common.filter.XssFilter;
import org.springframework.boot.web.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器配置对象，用于配置自定义的过滤器
 *
 * @author ZOKE
 * @date 2018-9-22 / 下午 05:20
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new XssFilter());
        filterRegistration.setEnabled(true);
        //配置需要拦截的路径，原来web.xml配置的init-param参数可用addInitParameter()方法添加
        filterRegistration.addUrlPatterns("/mybatis", "/thymeleaf", "/validate", "/template");
        //设置拦截器拦截的前后顺序
        filterRegistration.setOrder(1);
        return filterRegistration;
    }

    /**
     * 显示配置 HiddenHttpMethodFilter 以确保 setOrder(1) 方法生效
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new OrderedHiddenHttpMethodFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                filterChain.doFilter(request, response);
            }
        };
    }

}