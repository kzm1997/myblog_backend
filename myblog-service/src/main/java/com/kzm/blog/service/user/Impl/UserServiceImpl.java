package com.kzm.blog.service.user.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.constants.Base;
import com.kzm.blog.common.entity.User.Bo.UserLoginBo;
import com.kzm.blog.common.entity.User.Bo.UserRegisterBo;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.mapper.user.UserMapper;
import com.kzm.blog.service.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:50 2020/2/28
 * @Version
 */
@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result register(UserRegisterBo userRegisterBo) {
        //检查账号唯一性
        UserEntity one = userMapper.selectOne(Wrappers.<UserEntity>lambdaQuery().
                eq(UserEntity::getAccount, userRegisterBo.getAccount()));
        if (one != null) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }
        //检查邮箱
        UserEntity two = userMapper.selectOne(Wrappers.<UserEntity>lambdaQuery()
                .eq(UserEntity::getEmail, userRegisterBo.getEmail()));
        if (two != null) {
            return Result.error(ResultCode.EMAIL_HAS_EXISTED);
        }

        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userRegisterBo, user);
        int i = userMapper.insert(user);
        if (i == 0) {
            return Result.error(ResultCode.USER_Register_ERROR);
        }
        //to do 发邮件激活
        return Result.success();
    }

    @Override
    public Result login(UserLoginBo userLoginBo, HttpServletRequest httpServletRequest) {
        UserEntity userEntity = userMapper.selectOne(Wrappers.<UserEntity>lambdaQuery()
                .eq(UserEntity::getAccount, userLoginBo.getAccount()));
        if (userEntity==null){
             return Result.error(ResultCode.USER_NOT_EXIST);
        }
        UserEntity user = userMapper.selectOne(Wrappers.<UserEntity>lambdaQuery()
                .eq(UserEntity::getAccount, userLoginBo.getAccount())
                .eq(UserEntity::getPassword, userLoginBo.getPassword()));
        if (user==null){
            return Result.error(ResultCode.USER_LOGIN_ERROR);
        }
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(Base.CURRENT_USER,user);
        return Result.success();
    }
}
