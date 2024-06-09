package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddRoleDto;
import com.taoshao.domain.dto.RoleListDto;
import com.taoshao.domain.dto.RoleStatusDto;
import com.taoshao.domain.dto.UpdateRoleDto;
import com.taoshao.domain.entity.Role;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.domain.vo.RoleVo;

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

    /**
     * 分页查询角色列表
     * @param pageNum
     * @param pageSize
     * @param roleListDto
     * @return
     */
    PageVo pageRoleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto);

    /**
     * 修改角色的停启用状态
     * @param roleStatusDto
     * @return
     */
    ResponseResult changeStatus(RoleStatusDto roleStatusDto);

    /**
     * 新增角色
     * @param addRoleDto
     * @return
     */
    ResponseResult add(AddRoleDto addRoleDto);

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    RoleVo getRoleById(Long id);

    /**
     * 更新角色信息
     * @param updateRoleDto
     * @return
     */
    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    /**
     * 删除角色
     * @param id
     * @return
     */
    ResponseResult delete(Long id);

    /**
     * 列出所有角色
     *
     * @return
     */
    List<Role> listAllRole();
}

