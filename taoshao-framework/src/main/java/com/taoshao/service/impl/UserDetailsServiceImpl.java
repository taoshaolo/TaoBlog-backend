package com.taoshao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.taoshao.constants.SystemConstants;
import com.taoshao.domain.entity.LoginUser;
import com.taoshao.domain.entity.User;
import com.taoshao.mapper.MenuMapper;
import com.taoshao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.taoshao.constants.SystemConstants.ADMIN;

/**
 * @Author taoshao
 * @Date 2024/5/30
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        //判断是否查到用户，如果没有查得到抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //返回用户信息
        //  查询权限信息封装
        //  如果是后台用户才需要出现权限封装
        if (user.getType().equals(ADMIN)){
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,list);
        }
        return new LoginUser(user,null);
    }
}
