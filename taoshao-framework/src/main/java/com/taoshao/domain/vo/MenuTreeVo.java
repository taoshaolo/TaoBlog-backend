package com.taoshao.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 *
 * @author taoshao
 * @since 2024-06-05 10:53:58
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuTreeVo {
    //菜单ID
    private Long id;
    //菜单名称
    private String label;
    //父菜单ID
    private Long parentId;
    //子菜单
    private List<MenuTreeVo> children;

}

