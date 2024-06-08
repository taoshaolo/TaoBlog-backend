package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.MenuListDto;
import com.taoshao.domain.entity.Menu;
import com.taoshao.domain.vo.MenuListVo;
import com.taoshao.domain.vo.MenuVo;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author taoshao
 * @since 2024-06-05 10:53:58
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据用户id查询权限信息
     * @param id
     * @return
     */
    List<String> selectPermsByUserId(Long id);

    /**
     * 根据用户id查询路由菜单树
     * @param userId
     * @return
     */
    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    /**
     * 菜单列表
     *
     * @param menuListDto
     * @return
     */
    List<MenuListVo> menuList(MenuListDto menuListDto);

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    ResponseResult add(Menu menu);

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    MenuVo getMenuById(Long id);

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    ResponseResult updateMenu(Menu menu);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    ResponseResult delete(Long id);
}

