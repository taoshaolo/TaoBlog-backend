package com.taoshao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taoshao.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author taoshao
 * @since 2024-06-05 10:59:26
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询角色信息
     * @param id
     * @return
     */
    List<String> selectRoleKeyByUserId(Long id);
}

