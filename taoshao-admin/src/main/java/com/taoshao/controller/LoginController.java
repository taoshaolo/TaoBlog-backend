package com.taoshao.controller;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.LoginUser;
import com.taoshao.domain.entity.Menu;
import com.taoshao.domain.entity.User;
import com.taoshao.domain.enums.AppHttpCodeEnum;
import com.taoshao.domain.vo.AdminUserInfoVo;
import com.taoshao.domain.vo.RoutersVo;
import com.taoshao.domain.vo.UserInfoVo;
import com.taoshao.exception.SystemException;
import com.taoshao.service.BlogLoginService;
import com.taoshao.service.LoginService;
import com.taoshao.service.MenuService;
import com.taoshao.service.RoleService;
import com.taoshao.utils.BeanCopyUtils;
import com.taoshao.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author taoshao
 * @Date 2024/5/30
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示必须传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        //获取当前登录的用户id
        Long userId = SecurityUtils.getUserId();
        //查询 menu 结果是 tree 的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

}