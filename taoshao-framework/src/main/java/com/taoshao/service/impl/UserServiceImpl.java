package com.taoshao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.taoshao.domain.entity.User;
import com.taoshao.mapper.UserMapper;
import com.taoshao.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2024-06-01 17:57:51
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

