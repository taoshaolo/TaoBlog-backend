package com.taoshao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taoshao.domain.entity.Menu;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author taoshao
 * @since 2024-06-05 10:53:58
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户id查询权限信息
     * @param id
     * @return
     */
    List<String> selectPermsByUserId(Long id);

    /**
     * 查询所有路由菜单
     * @return
     */
    List<Menu> selectAllRouterMenu();

    /**
     * 根据用户id查询路由菜单树
     * @param userId
     * @return
     */
    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}

