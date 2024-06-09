package com.taoshao.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author taoshao
 * @Date 2024/5/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoryVo {

    private Long id;

    private String name;

    private String description;

    private String status;
}
