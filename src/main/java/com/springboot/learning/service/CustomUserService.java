package com.springboot.learning.service;

import com.springboot.learning.dao.SysUserRepository;
import com.springboot.learning.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserService implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user =  sysUserRepository.findByUsername(s);
        System.out.println(user.getUsername()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if(user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}
