package com.taoshao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddUserDto;
import com.taoshao.domain.dto.UpdateUserDto;
import com.taoshao.domain.dto.UserListDto;
import com.taoshao.domain.entity.User;
import com.taoshao.domain.vo.GetUserInfoVo;
import com.taoshao.domain.vo.PageVo;


/**
 * 用户表(User)表服务接口
 *
 * @author taoshao
 * @since 2024-06-01 17:57:49
 */
public interface UserService extends IService<User> {

    /**
     * 用户信息
     * @return
     */
    ResponseResult userInfo();

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    ResponseResult updateUserInfo(User user);

    /**
     * 注册
     * @param user
     * @return
     */
    ResponseResult register(User user);

    /**
     * 用户分页列表
     * @param pageNum
     * @param pageSize
     * @param userListDto
     * @return
     */
    PageVo pageUserList(Integer pageNum, Integer pageSize, UserListDto userListDto);

    /**
     * 新增用户
     * @param addUserDto
     * @return
     */
    ResponseResult add(AddUserDto addUserDto);

    /**
     * 删除用户
     * @param id
     * @return
     */
    ResponseResult delete(Long id);

    /**
     * 根据id查询用户信息
     * @return
     */
    GetUserInfoVo getUserById(Long id);

    /**
     * 更新用户信息
     * @param updateUserDto
     * @return
     */
    ResponseResult updateUser(UpdateUserDto updateUserDto);
}

