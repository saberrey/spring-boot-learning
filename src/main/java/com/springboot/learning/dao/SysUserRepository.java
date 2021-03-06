package com.springboot.learning.dao;

import com.springboot.learning.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser,Long> {
    SysUser findByUsername(String username);
}
