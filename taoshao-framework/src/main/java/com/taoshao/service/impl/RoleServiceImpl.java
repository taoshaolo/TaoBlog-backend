package com.taoshao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddRoleDto;
import com.taoshao.domain.dto.RoleListDto;
import com.taoshao.domain.dto.RoleStatusDto;
import com.taoshao.domain.dto.UpdateRoleDto;
import com.taoshao.domain.entity.Role;
import com.taoshao.domain.entity.RoleMenu;
import com.taoshao.domain.enums.AppHttpCodeEnum;
import com.taoshao.domain.vo.PageRoleListVo;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.domain.vo.RoleVo;
import com.taoshao.mapper.RoleMapper;
import com.taoshao.service.RoleMenuService;
import com.taoshao.service.RoleService;
import com.taoshao.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author taoshao
 * @since 2024-06-05 10:59:26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有 admin
        if (id == 1L) {
            List<String> roleKey = new ArrayList<>();
            roleKey.add("admin");
            return roleKey;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public PageVo pageRoleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleListDto.getRoleName()), Role::getRoleName, roleListDto.getRoleName());
        queryWrapper.eq(StringUtils.hasText(roleListDto.getStatus()), Role::getStatus, roleListDto.getStatus());
        queryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        List<PageRoleListVo> pageRoleListVos = page(page, queryWrapper)
                .getRecords()
                .stream()
                .map(role -> {
                    return BeanCopyUtils.copyBean(role, PageRoleListVo.class);
                })
                .collect(Collectors.toList());

        PageVo pageVo = new PageVo(pageRoleListVos, page.getTotal());
        return pageVo;
    }

    @Override
    public ResponseResult changeStatus(RoleStatusDto roleStatusDto) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Role::getId, roleStatusDto.getRoleId());

        Role role = BeanCopyUtils.copyBean(roleStatusDto, Role.class);
        boolean updateResult = update(role, updateWrapper);
        if (updateResult) {
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "更新失败，没有找到匹配的记录或更新条件不正确");
    }

    @Override
    public ResponseResult add(AddRoleDto addRoleDto) {
        //添加 角色
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);

        List<RoleMenu> roleMenus = addRoleDto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        //添加 角色和菜单关联
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public RoleVo getRoleById(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return roleVo;
    }

    @Override
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        //更新 角色基本信息
        Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
        updateById(role);

        //更新 角色和菜单关联
        List<RoleMenu> roleMenus = updateRoleDto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public List<Role> listAllRole() {
        List<Role> list = list();
        return list;
    }

}

