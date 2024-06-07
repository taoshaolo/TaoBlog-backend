package com.taoshao.service.impl;

import com.taoshao.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author taoshao
 * @Date 2024/6/7
 */
@Service("ps")
public class PermissionService {

    /**
     * 判断当前是否具有 permission
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission){
        //如果是超级管理员 直接返回true
        if (SecurityUtils.isAdmin()){
            return true;
        }
        //否则 获取单曲登录用户所具有的权限列表 如何判断是否存在permission
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
