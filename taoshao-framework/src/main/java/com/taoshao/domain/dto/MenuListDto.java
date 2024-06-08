package com.taoshao.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单权限表(Menu)Dto
 *
 * @author taoshao
 * @since 2024-06-05 10:53:58
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuListDto {

    //菜单名称
    private String menuName;
    //菜单状态（0正常 1停用）
    private String status;

}

