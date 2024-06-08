package com.taoshao.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author taoshao
 * @Date 2024/6/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuTreeVo {

    // 菜单树
    private List<MenuTreeVo> menus;

    // 角色所关联的菜单权限ID列表
    private List<Long> checkedKeys;
}
