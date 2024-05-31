package com.taoshao.controller;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.entity.User;
import com.taoshao.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author taoshao
 * @Date 2024/5/30
 */
@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {

        return blogLoginService.login(user);
    }
}
