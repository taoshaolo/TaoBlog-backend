package com.taoshao.filter;

import com.alibaba.fastjson.JSON;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.LoginUser;
import com.taoshao.domain.enums.AppHttpCodeEnum;
import com.taoshao.utils.JwtUtil;
import com.taoshao.utils.RedisCache;
import com.taoshao.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.taoshao.constants.RedisConstants.LOGIN_KEY;

/**
 * @Author taoshao
 * @Date 2024/5/30
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的 token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //说明该接口不需要登录，直接放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析获取 userId
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token 超时 或 非法
            //响应告诉前端需要重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        //从 redis 中获取用户信息
        String json = redisCache.getCacheObject(LOGIN_KEY + userId).toString();
        LoginUser loginUser = JSON.parseObject(json, LoginUser.class);

        //如果获取不到
        if (Objects.isNull(loginUser)) {
            //说明登录过期 提示重新登录
            ResponseResult result =
                    ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        //存入 SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
