package com.taoshao.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author taoshao
 * @since 2024-06-05 10:59:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryDto {
    //id
    private Long id;
    //分类名称
    private String name;
    //描述
    private String description;
    //角色状态（0正常 1停用）
    private String status;

}

