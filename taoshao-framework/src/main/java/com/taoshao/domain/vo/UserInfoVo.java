package com.taoshao.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author taoshao
 * @Date 2024/5/30
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {

    private Long id;

    private String nickName;

    private String avatar;

    private String sex;

    private String email;
}
