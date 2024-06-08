package com.taoshao.controller;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddRoleDto;
import com.taoshao.domain.dto.RoleListDto;
import com.taoshao.domain.dto.RoleStatusDto;
import com.taoshao.domain.dto.UpdateRoleDto;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.domain.vo.RoleVo;
import com.taoshao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author taoshao
 * @Date 2024/6/8
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        PageVo pageVo = roleService.pageRoleList(pageNum, pageSize, roleListDto);
        return ResponseResult.okResult(pageVo);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleStatusDto roleStatusDto) {
        return roleService.changeStatus(roleStatusDto);
    }

    @PostMapping
    public ResponseResult add(@RequestBody AddRoleDto addRoleDto) {
        return roleService.add(addRoleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult<RoleVo> getRoleById(@PathVariable Long id) {
        RoleVo roleVo = roleService.getRoleById(id);
        return ResponseResult.okResult(roleVo);
    }

    @PutMapping
    public ResponseResult update(@RequestBody UpdateRoleDto updateRoleDto) {
        return roleService.updateRole(updateRoleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        return roleService.delete(id);
    }

}
