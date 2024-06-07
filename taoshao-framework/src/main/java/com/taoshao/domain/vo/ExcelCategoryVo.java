package com.taoshao.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
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
public class ExcelCategoryVo {

    @ExcelProperty("分类")
    private String name;
    @ExcelProperty("描述")
    private String description;
    @ExcelProperty("状态0：正常，1：禁用")
    private String status;
}
