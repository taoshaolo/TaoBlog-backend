package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2024-06-01 17:57:49
 */
public interface UserService extends IService<User> {

    /**
     * 用户信息
     * @return
     */
    ResponseResult userInfo();
}

