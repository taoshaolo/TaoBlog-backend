package com.taoshao.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author taoshao
 * @Date 2024/6/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDto {

    //文章标题
    private String title;
    //文章摘要
    private String summary;
}
