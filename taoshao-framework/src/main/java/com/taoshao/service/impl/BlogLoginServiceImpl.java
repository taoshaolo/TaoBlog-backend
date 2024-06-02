package com.taoshao.service.impl;

import com.taoshao.utils.RedisCache;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.LoginUser;
import com.taoshao.domain.entity.User;
import com.taoshao.domain.vo.BlogUserLoginVo;
import com.taoshao.domain.vo.UserInfoVo;
import com.taoshao.service.BlogLoginService;
import com.taoshao.utils.BeanCopyUtils;
import com.taoshao.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author taoshao
 * @Date 2024/5/30
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //获取 userId 生成 token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入 redis
        redisCache.setCacheObject("bloglogin:" + userId, loginUser);

        //把 User 转换成 UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        //把 token和 userinfo 封装返回
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取 token 解析获取 userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取 userId
        Long userId = loginUser.getUser().getId();
        //删除 redis 中的用户信息
        redisCache.deleteObject("bloglogin:" + userId);
        return ResponseResult.okResult();
    }
}
