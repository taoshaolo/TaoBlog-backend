package com.taoshao.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author taoshao
 * @since 2024-06-05 10:53:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuListVo {
    //菜单ID
    private Long id;
    //菜单名称
    private String menuName;
    //父菜单ID
    private Long parentId;
    //显示顺序
    private Integer orderNum;
    //路由地址
    private String path;
    //组件路径
    private String component;
    //是否为外链（0是 1否）
    private Integer isFrame;
    //菜单类型（M目录 C菜单 F按钮）
    private String menuType;
    //菜单状态（0显示 1隐藏）
    private String visible;
    //菜单状态（0正常 1停用）
    private String status;
    //权限标识
    private String perms;
    //菜单图标
    private String icon;
    //备注
    private String remark;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}

