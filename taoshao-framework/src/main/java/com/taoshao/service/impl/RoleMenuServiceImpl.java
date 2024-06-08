package com.taoshao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoshao.domain.entity.RoleMenu;
import com.taoshao.mapper.RoleMenuMapper;
import com.taoshao.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author taoshao
 * @since 2024-06-08 16:57:40
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

