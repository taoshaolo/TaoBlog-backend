package com.taoshao.domain.dto;

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
public class UpdateRoleDto {

    //角色id
    private Long id;
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    //菜单ids
    private List<Long> menuIds;
    //备注
    private String remark;
}
