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
public class PageLinkListVo {

    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;

    private String status;

}
