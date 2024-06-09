package com.taoshao.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @author taoshao
 * @since 2024-05-30 17:44:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {

    //用户名
    private String userName;

    //账号状态（0正常 1停用）
    private String status;

    //手机号
    private String phonenumber;

}

