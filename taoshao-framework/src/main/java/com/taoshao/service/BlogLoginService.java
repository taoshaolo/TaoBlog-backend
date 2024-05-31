package com.taoshao.service;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.User;

/**
 * @Author taoshao
 * @Date 2024/5/30
 */
public interface BlogLoginService {
    ResponseResult login(User user);
}
