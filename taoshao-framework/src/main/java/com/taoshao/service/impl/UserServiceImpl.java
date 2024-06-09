package com.taoshao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.AddUserDto;
import com.taoshao.domain.dto.UpdateUserDto;
import com.taoshao.domain.dto.UserListDto;
import com.taoshao.domain.entity.Role;
import com.taoshao.domain.entity.User;
import com.taoshao.domain.entity.UserRole;
import com.taoshao.domain.enums.AppHttpCodeEnum;
import com.taoshao.domain.vo.*;
import com.taoshao.exception.SystemException;
import com.taoshao.mapper.RoleMapper;
import com.taoshao.mapper.UserMapper;
import com.taoshao.mapper.UserRoleMapper;
import com.taoshao.service.RoleService;
import com.taoshao.service.UserRoleService;
import com.taoshao.service.UserService;
import com.taoshao.utils.BeanCopyUtils;
import com.taoshao.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author taoshao
 * @since 2024-06-01 17:57:51
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在进行判断
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (emailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        return count(queryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) > 0;
    }


    @Override
    public PageVo pageUserList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userListDto.getUserName()), User::getUserName, userListDto.getUserName());
        queryWrapper.eq(StringUtils.hasText(userListDto.getPhonenumber()), User::getPhonenumber, userListDto.getPhonenumber());
        queryWrapper.eq(StringUtils.hasText(userListDto.getStatus()), User::getStatus, userListDto.getStatus());

        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        //User 封装成 UserListVo
        List<UserListVo> userListVos = page(page, queryWrapper).getRecords()
                .stream()
                .map(user -> BeanCopyUtils.copyBean(user, UserListVo.class))
                .collect(Collectors.toList());

        PageVo pageVo = new PageVo(userListVos, page.getTotal());
        return pageVo;
    }

    @Override
    public ResponseResult add(AddUserDto addUserDto) {
        String userName = addUserDto.getUserName();
        String phonenumber = addUserDto.getPhonenumber();
        String email = addUserDto.getEmail();
        //用户名不能为空
        if (userName == null) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        LambdaQueryWrapper<User> userNameQueryWrapper = new LambdaQueryWrapper<>();
        userNameQueryWrapper.eq(User::getUserName, userName);
        //用户名必须之前未存在
        if (count(userNameQueryWrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //手机号必须之前未存在
        LambdaQueryWrapper<User> phonenumberQueryWrapper = new LambdaQueryWrapper<>();
        phonenumberQueryWrapper.eq(User::getPhonenumber, phonenumber);
        if (count(phonenumberQueryWrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        //邮箱必须之前未存在
        LambdaQueryWrapper<User> emailQueryWrapper = new LambdaQueryWrapper<>();
        emailQueryWrapper.eq(User::getEmail, email);
        if (count(emailQueryWrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        User user = new User();
        //拷贝
        BeanUtils.copyProperties(addUserDto, user);
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(addUserDto.getPassword());
        user.setPassword(encodePassword);
        //添加 基本信息
        save(user);

        //添加 用户和角色关联
        List<UserRole> userRoles = addUserDto.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());

        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long id) {
        Long userId = SecurityUtils.getUserId();
        if (id.equals(userId)) {
            throw new SystemException(AppHttpCodeEnum.NOT_DELETE);
        }
        userMapper.deleteById(id);
        return ResponseResult.okResult();
    }


    @Override
    public GetUserInfoVo getUserById(Long id) {
        User user = getById(id);
        UserVo userVo = BeanCopyUtils.copyBean(user, UserVo.class);
        GetUserInfoVo getUserInfoVo = new GetUserInfoVo();
        //回显 用户信息
        getUserInfoVo.setUser(userVo);

        //回显 用户所关联的角色id列表
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, id);
        List<Long> roleIds = userRoleService.list(queryWrapper)
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        getUserInfoVo.setRoleIds(roleIds);

        //回显 所有角色的列表
        List<Role> roles = roleService.list().stream()
                .filter(role -> role.getStatus().equals("0"))
                .collect(Collectors.toList());

        getUserInfoVo.setRoles(roles);

        return getUserInfoVo;
    }

    @Override
    // 添加事务注解
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateUser(UpdateUserDto updateUserDto) {

        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        //更新基本数据
        updateById(user);

        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,user.getId());
        // 清理旧的角色关联
        userRoleService.remove(queryWrapper);

        //更新 用户和角色关联
        List<UserRole> userRoles = updateUserDto.getRoleIds()
                .stream()
                .distinct()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());

        userRoleService.saveBatch(userRoles);


        return ResponseResult.okResult();
    }
}

