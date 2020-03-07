package com.springboot.learning.web.Filter;

import com.springboot.learning.entity.SysUser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class MyFilter implements Filter {

    //多长时间内连续登陆会被封禁
    private final int accessTime = 100*1000;

    //连续访问几次会被禁
    private final int accessNum = 2;

    //每次禁多久
    private final int limitTime = 10*1000;

    //获取servlet context
    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void destroy() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        ServletContext sc = config.getServletContext();

        ConcurrentHashMap<String,Long> limitMap = (ConcurrentHashMap<String, Long>) sc.getAttribute("limitedIP");


        String ip = request.getRemoteAddr();

        if(expireOrNot(ip,limitMap)){
            request.getRequestDispatcher("/limited").forward(servletRequest,
                servletResponse);
            return;
        }
        //long数组的第一个是访问次数，第二个是第一次访问的时间
        ConcurrentHashMap<String, Long[]> accessRecord = (ConcurrentHashMap<String, Long[]>)sc.getAttribute("accessRecord");

        if(limitOrNot(ip,accessRecord)){
            limitMap.put(ip, System.currentTimeMillis()+limitTime);
            request.getRequestDispatcher("/limited").forward(servletRequest,
                servletResponse);
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    protected boolean limitOrNot(String ip, ConcurrentHashMap<String, Long[]> accessRecord){
        if(accessRecord.containsKey(ip)){
            Long[] record = accessRecord.get(ip);
            if(record[0] + 1 > accessNum){
                if(System.currentTimeMillis()-record[1]>accessTime){
                    record[0] = 1L;
                    record[1] = System.currentTimeMillis();
                    return false;
                }
                record[0] = 1L;
                record[1] = System.currentTimeMillis();
                return true;
            }else {
                record[0]++;
                return false;
            }
        }else{
            Long[] record = new Long[2];
            record[0] = 1L;
            record[1] = System.currentTimeMillis();
            accessRecord.put(ip,record);
            return false;
        }
    }

    protected boolean expireOrNot(String ip, ConcurrentHashMap<String,Long> limitedList){
        Long time = System.currentTimeMillis();
        if(limitedList.containsKey(ip)){
            return time-limitedList.get(ip)>accessTime;
        }else {
            return false;
        }
    }

}















