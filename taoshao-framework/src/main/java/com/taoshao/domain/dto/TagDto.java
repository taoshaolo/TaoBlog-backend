package com.taoshao.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author taoshao
 * @Date 2024/6/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {

    private Long id;
    private String name;
    private String remark;
}
