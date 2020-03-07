package com.springboot.learning.web.Listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext();
        ConcurrentHashMap<String, Long> expireMap = new ConcurrentHashMap<>();
        sce.getServletContext().setAttribute("limitedIP", expireMap);
        ConcurrentHashMap<String, Long[]> accessRecord = new ConcurrentHashMap<>();
        sce.getServletContext().setAttribute("accessRecord",accessRecord);
        log.info("初始化ip拦截记录...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
