package com.kzm.blog.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.entity.User.Bo.UserLoginBo;
import com.kzm.blog.common.entity.User.Bo.UserRegisterBo;
import com.kzm.blog.common.entity.User.UserEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<UserEntity> {

      Result register(UserRegisterBo userRegisterBo);

      Result login(UserLoginBo userLoginBo, HttpServletRequest request);
}
