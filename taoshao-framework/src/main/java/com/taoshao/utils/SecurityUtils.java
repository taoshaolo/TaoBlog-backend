package com.taoshao.utils;

import com.taoshao.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtils {
    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        if (!Objects.isNull(getAuthentication())){
            return (LoginUser) getAuthentication().getPrincipal();
        }
        return null;
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin() {
        if (!Objects.isNull(getLoginUser())) {
            Long id = getLoginUser().getUser().getId();
            return id != null && id.equals(1L);
        }
        return false;
    }

    public static Long getUserId() {
        if (!Objects.isNull(getLoginUser())){
            return getLoginUser().getUser().getId();
        }
        return null;
    }
}
