package com.kzm.blog.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.entity.User.Bo.UserLoginBo;
import com.kzm.blog.common.entity.User.Bo.UserRegisterBo;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.exception.KBlogException;
import com.kzm.blog.common.exception.RedisException;


public interface UserService extends IService<UserEntity> {

      void register(UserRegisterBo userRegisterBo) throws KBlogException, RedisException, JsonProcessingException;

      Result login(UserLoginBo userLoginBo) throws KBlogException, RedisException;
}
