package com.springboot.learning.config;

import com.springboot.learning.entity.EnableLimitedIpAccess;
import com.springboot.learning.web.Filter.MyFilter;
import com.springboot.learning.web.Listener.MyListener;
import com.springboot.learning.web.servlet.MyServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.Repository;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //注册新的servlet
    @ConditionalOnBean(EnableLimitedIpAccess.class)
    @Bean
    public ServletRegistrationBean registerNewServlet(){
        ServletRegistrationBean<MyServlet> servletRegistration = new ServletRegistrationBean<>();
        servletRegistration.addUrlMappings("/filterTest");
        servletRegistration.setServlet(new MyServlet());
        servletRegistration.setLoadOnStartup(2);
        return servletRegistration;
    }

    //注册新的filter，这里的filter是用的ip拦截的例子
    @ConditionalOnBean(EnableLimitedIpAccess.class)
    @Bean
    public FilterRegistrationBean registerNewFilter(){
        FilterRegistrationBean<MyFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new MyFilter());
        filterRegistration.addUrlPatterns("/filterTest");
        return filterRegistration;
    }

    //注册新的Listener，用的例子是对ip拦截功能的servlet context进行初始化。
    @ConditionalOnBean(EnableLimitedIpAccess.class)
    @Bean
    public ServletListenerRegistrationBean registerNewListener(){
        ServletListenerRegistrationBean<MyListener> listenRegistration = new ServletListenerRegistrationBean<>();
        listenRegistration.setListener(new MyListener());
        return listenRegistration;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/test").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }
}
