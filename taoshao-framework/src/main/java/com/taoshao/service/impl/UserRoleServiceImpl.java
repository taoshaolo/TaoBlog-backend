package com.taoshao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoshao.domain.entity.UserRole;
import com.taoshao.mapper.UserRoleMapper;
import com.taoshao.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2024-06-08 21:37:57
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

