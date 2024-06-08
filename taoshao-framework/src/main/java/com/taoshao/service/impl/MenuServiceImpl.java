package com.taoshao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.MenuListDto;
import com.taoshao.domain.entity.Menu;
import com.taoshao.domain.entity.RoleMenu;
import com.taoshao.domain.vo.MenuListVo;
import com.taoshao.domain.vo.MenuTreeVo;
import com.taoshao.domain.vo.MenuVo;
import com.taoshao.domain.vo.RoleMenuTreeVo;
import com.taoshao.exception.SystemException;
import com.taoshao.mapper.MenuMapper;
import com.taoshao.mapper.RoleMenuMapper;
import com.taoshao.service.MenuService;
import com.taoshao.utils.BeanCopyUtils;
import com.taoshao.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.taoshao.constants.SystemConstants.*;
import static com.taoshao.domain.enums.AppHttpCodeEnum.MENU_NOT_EXIST;
import static com.taoshao.domain.enums.AppHttpCodeEnum.SYSTEM_ERROR;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author taoshao
 * @since 2024-06-05 10:53:58
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员返回所有的权限
        if (SecurityUtils.isAdmin()) {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, MENU, BUTTON);
            queryWrapper.eq(Menu::getStatus, STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回 所具有的权限

        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        //判断是否是管理员
        List<Menu> menus = null;
        if (SecurityUtils.isAdmin()) {
            //如果是 获取所有符合要求的 Menu
            menus = menuMapper.selectAllRouterMenu();

        } else {
            //否则 获取当前用户所具有的 Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建 tree
        //先找出第一层的菜单，然后去找他们的子菜单设置得到 children 属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }

    @Override
    public List<MenuListVo> menuList(MenuListDto menuListDto) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(menuListDto.getStatus()),Menu::getStatus,menuListDto.getStatus());
        queryWrapper.eq(StringUtils.hasText(menuListDto.getMenuName()),Menu::getMenuName,menuListDto.getMenuName());

        List<Menu> list = list(queryWrapper);
        List<MenuListVo> menuListVos = BeanCopyUtils.copyBeanList(list, MenuListVo.class);
        return menuListVos;
    }

    @Override
    public ResponseResult add(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public MenuVo getMenuById(Long id) {
        Menu menu = getById(id);
        if (menu == null){
            throw new SystemException(MENU_NOT_EXIST);
        }
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return menuVo;
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        //检查父菜单ID是否与当前菜单ID相同，不能把父菜单设置为当前菜单
        if (menu.getParentId().equals(menu.getId())){
            return ResponseResult.errorResult(SYSTEM_ERROR,"修改菜单失败，上级菜单不能选择自己");
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long id) {
        //要删除的菜单有子菜单,则不能删除
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,id);
        int count = count(queryWrapper);
        if (count > 0){
            return ResponseResult.errorResult(SYSTEM_ERROR,"存在子菜单不允许删除");
        }
        //没有,可以删除
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public List<MenuTreeVo> treeSelect() {
        List<Menu> list = list();
        List<MenuTreeVo> menuTreeVos = new ArrayList<>();

        for (Menu menu : list) {
            //手动赋值属性
            MenuTreeVo menuTreeVo = new MenuTreeVo();
            menuTreeVo.setId(menu.getId());
            menuTreeVo.setLabel(menu.getMenuName());
            menuTreeVo.setParentId(menu.getParentId());
            //添加到 menuTreeVos 集合里
            menuTreeVos.add(menuTreeVo);
        }

        List<MenuTreeVo> treeVos = builder2MenuTree(menuTreeVos, 0L);

        return treeVos;
    }

    private List<MenuTreeVo> builder2MenuTree(List<MenuTreeVo> menuTreeVos, Long parentId) {
        List<MenuTreeVo> treeVos = menuTreeVos.stream()
                .filter(menuTreeVo -> menuTreeVo.getParentId().equals(parentId))
                .map(menuTreeVo -> menuTreeVo.setChildren(get2Children(menuTreeVo,menuTreeVos )))
                .collect(Collectors.toList());
        return treeVos;
    }

    private List<MenuTreeVo> get2Children(MenuTreeVo menuTreeVo, List<MenuTreeVo> menuTreeVos) {
        List<MenuTreeVo> childrenList = menuTreeVos.stream()
                .filter(m -> m.getParentId().equals(menuTreeVo.getId()))
                .map(m->m.setChildren(get2Children(m,menuTreeVos)))
                .collect(Collectors.toList());
        return childrenList;
    }

    @Override
    public RoleMenuTreeVo roleMenuTreeSelectById(Long id) {

        List<MenuTreeVo> menuTreeVos = treeSelect();

        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        queryWrapper.select(RoleMenu::getMenuId);

        List<Long> menuIds = roleMenuMapper.selectList(queryWrapper).stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());

        RoleMenuTreeVo roleMenuTreeVo = new RoleMenuTreeVo(menuTreeVos, menuIds);
        return roleMenuTreeVo;
    }
}

