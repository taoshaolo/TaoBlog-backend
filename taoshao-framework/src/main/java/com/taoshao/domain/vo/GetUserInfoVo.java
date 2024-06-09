package com.taoshao.domain.vo;

import com.taoshao.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author taoshao
 * @Date 2024/6/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoVo {

    private List<Long> roleIds;

    private List<Role> roles;

    private UserVo user;
}
