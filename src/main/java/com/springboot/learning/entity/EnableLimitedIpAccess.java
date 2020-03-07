package com.springboot.learning.entity;


import org.springframework.context.annotation.Configuration;

//不加Configuration会有问题，还没等它进入ioc就会运行filter的初始化，这个问题是有关加载的时机的
//TODO：加载时机探究
@Configuration
public class EnableLimitedIpAccess {
    public EnableLimitedIpAccess(){

    }
}
