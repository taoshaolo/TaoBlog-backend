package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author taoshao
 * @since 2024-06-05 10:59:26
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户id查询角色信息
     * @param id
     * @return
     */
    List<String> selectRoleKeyByUserId(Long id);
}

