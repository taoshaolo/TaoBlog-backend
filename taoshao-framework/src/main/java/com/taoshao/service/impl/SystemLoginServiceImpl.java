package com.taoshao.service.impl;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.LoginUser;
import com.taoshao.domain.entity.User;
import com.taoshao.service.LoginService;
import com.taoshao.utils.JwtUtil;
import com.taoshao.utils.RedisCache;
import com.taoshao.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.taoshao.constants.RedisConstants.LOGIN_KEY;

/**
 * @Author taoshao
 * @Date 2024/5/30
 */
@Service
public class SystemLoginServiceImpl implements LoginService {

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
        redisCache.setCacheObject(LOGIN_KEY + userId, loginUser);

        //把 token 封装返回
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取 userId
        Long userId = SecurityUtils.getUserId();
        //删除redis中的用户信息
        redisCache.deleteObject(LOGIN_KEY + userId);
        return ResponseResult.okResult();
    }

}
