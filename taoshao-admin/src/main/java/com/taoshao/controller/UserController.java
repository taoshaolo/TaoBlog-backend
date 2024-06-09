package com.taoshao.controller;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddUserDto;
import com.taoshao.domain.dto.UpdateUserDto;
import com.taoshao.domain.dto.UserListDto;
import com.taoshao.domain.vo.GetUserInfoVo;
import com.taoshao.domain.vo.PageVo;
import com.taoshao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author taoshao
 * @Date 2024/6/8
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        PageVo pageVo = userService.pageUserList(pageNum, pageSize, userListDto);
        return ResponseResult.okResult(pageVo);
    }

    @PostMapping
    public ResponseResult add(@RequestBody AddUserDto addUserDto) {
        return userService.add(addUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseResult<GetUserInfoVo> getUserById(@PathVariable Long id){
        GetUserInfoVo getUserInfoVo = userService.getUserById(id);
        return ResponseResult.okResult(getUserInfoVo);
    }

    @PutMapping
    public ResponseResult update(@RequestBody UpdateUserDto updateUserDto){
        return userService.updateUser(updateUserDto);
    }

}
