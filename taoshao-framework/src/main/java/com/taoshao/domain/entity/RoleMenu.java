package com.taoshao.domain.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 角色和菜单关联表(RoleMenu)表实体类
 *
 * @author taoshao
 * @since 2024-06-08 16:57:40
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
@Accessors(chain = true)
public class RoleMenu {
    //角色I
    private Long roleId;
    //菜单ID
    private Long menuId;

}

